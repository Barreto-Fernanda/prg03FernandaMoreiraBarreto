package br.com.ifba.usuario.service;

// Exceção de regra de negócio genérica (CPF inválido, senha curta, palavra proibida)
import br.com.ifba.infrastructure.exception.NegocioException;
// DAO de mentira: guarda os usuários numa lista, sem banco
import br.com.ifba.usuario.dao.FakeUsuarioDao;
// Entidade usada nos testes
import br.com.ifba.usuario.entity.Usuario;
// Exceção lançada quando o login já pertence a outro usuário
import br.com.ifba.usuario.exception.LoginDuplicadoException;
// Anotações do JUnit 5: @BeforeEach roda antes de cada teste
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Verificações: assertThrows confere a exceção; assertDoesNotThrow confere que nada é lançado
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Testa as REGRAS DE NEGÓCIO do UsuarioService, sem banco nenhum:
// o Service recebe o FakeUsuarioDao pelo construtor (injeção de dependência).
class UsuarioServiceTest {

    // Troque por uma palavra que exista na lista de proibidas do ValidadorUsuario
    private static final String PALAVRA_PROIBIDA = "admin";

    private FakeUsuarioDao dao;
    private UsuarioIService service;

    // Antes de cada teste: DAO vazio e Service novo, para um teste não afetar o outro
    @BeforeEach
    void preparar() {
        dao = new FakeUsuarioDao();
        service = new UsuarioService(dao);
    }

    // Monta um usuário que passa em todas as regras
    private Usuario usuarioValido(String login) {
        return new Usuario("Ana Souza", "11144477735", login, "Senha123");
    }

    // CAMINHO NORMAL: usuário válido é gravado e recebe id
    @Test
    void saveComDadosValidosGravaEGeraId() {
        Usuario salvo = assertDoesNotThrow(() -> service.save(usuarioValido("ana")));

        assertNotNull(salvo.getId());
        assertEquals(1, dao.findAll().size());
    }

    // Regra: CPF precisa ser válido
    @Test
    void saveComCpfInvalidoLancaNegocioException() {
        Usuario u = usuarioValido("ana");
        u.setCpf("123");

        NegocioException erro = assertThrows(NegocioException.class, () -> service.save(u));

        assertEquals("CPF inválido. Use apenas 11 números.", erro.getMessage());
        // A regra barrou antes do DAO: nada foi gravado
        assertTrue(dao.findAll().isEmpty());
    }

    // Regra: senha com no mínimo 6 caracteres
    @Test
    void saveComSenhaCurtaLancaNegocioException() {
        Usuario u = usuarioValido("ana");
        u.setSenha("123");

        NegocioException erro = assertThrows(NegocioException.class, () -> service.save(u));

        assertEquals("Senha muito curta. Use ao menos 6 caracteres.", erro.getMessage());
        assertTrue(dao.findAll().isEmpty());
    }

    // Regra: login não pode conter palavra proibida
    @Test
    void saveComLoginComPalavraProibidaLancaNegocioException() {
        Usuario u = usuarioValido(PALAVRA_PROIBIDA);

        NegocioException erro = assertThrows(NegocioException.class, () -> service.save(u));

        assertEquals("Login contém palavra não permitida.", erro.getMessage());
        assertTrue(dao.findAll().isEmpty());
    }

    // Regra: login repetido no cadastro
    @Test
    void saveComLoginRepetidoLancaLoginDuplicadoException() {
        service.save(usuarioValido("ana"));

        Usuario outro = new Usuario("Bruno Lima", "52998224725", "ana", "Senha123");

        LoginDuplicadoException erro = assertThrows(LoginDuplicadoException.class,
                () -> service.save(outro));

        assertEquals("ana", erro.getLogin());
        // Só o primeiro usuário está gravado
        assertEquals(1, dao.findAll().size());
    }

    // Regra: na edição, usar o login de OUTRO usuário é recusado
    @Test
    void updateComLoginDeOutroUsuarioLancaLoginDuplicadoException() {
        service.save(usuarioValido("ana"));
        Usuario bruno = service.save(
                new Usuario("Bruno Lima", "52998224725", "bruno", "Senha123"));

        bruno.setLogin("ana");

        assertThrows(LoginDuplicadoException.class, () -> service.update(bruno));
    }

    // CAMINHO NORMAL NA EDIÇÃO: manter o PRÓPRIO login não é "repetido"
    @Test
    void updateMantendoOProprioLoginNaoLancaExcecao() {
        Usuario ana = service.save(usuarioValido("ana"));

        ana.setTelefone("74999990001");

        Usuario atualizada = assertDoesNotThrow(() -> service.update(ana));

        assertEquals("74999990001", atualizada.getTelefone());
    }

    // As mesmas regras valem na edição (não só no cadastro)
    @Test
    void updateComSenhaCurtaLancaNegocioException() {
        Usuario ana = service.save(usuarioValido("ana"));

        ana.setSenha("123");

        assertThrows(NegocioException.class, () -> service.update(ana));
    }
}
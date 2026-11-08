package br.com.ifba.usuario.dao;

// Exceção de domínio que o DAO lança quando uma operação de persistência falha
import br.com.ifba.infrastructure.exception.PersistenciaException;
// Classe que entrega o EntityManager; aqui só serve para testar se o MySQL está de pé
import br.com.ifba.infrastructure.util.JPAUtil;
// Entidade usada nos testes
import br.com.ifba.usuario.entity.Usuario;
// Exceção de domínio lançada quando o login já pertence a outro usuário
import br.com.ifba.usuario.exception.LoginDuplicadoException;
// Lista que guarda os usuários criados, para apagar no fim de cada teste
import java.util.ArrayList;
import java.util.List;
// Anotações do JUnit 5: @BeforeAll roda uma vez, @BeforeEach e @AfterEach rodam em cada teste
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Verificações: assertThrows confere que a exceção esperada foi lançada
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
// Se o MySQL estiver fora do ar, os testes são pulados em vez de falhar
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// Testa o que o DAO faz quando a operação FALHA: ele deve lançar a exceção de domínio,
// e não deixar o erro técnico do Hibernate vazar para a tela.
// Estes testes usam o MySQL de verdade (o UsuarioDao real).
class UsuarioDaoTest {

    private UsuarioDao dao;

    // Usuários gravados durante o teste, para apagar no @AfterEach
    private final List<Usuario> criados = new ArrayList<>();

    // Roda uma vez antes de todos os testes: confere se o banco responde
    @BeforeAll
    static void verificarBanco() {
        boolean disponivel;
        try {
            JPAUtil.getEntityManager().close(); // abre e fecha uma conexão de teste
            disponivel = true;
        } catch (Exception e) {
            disponivel = false;
        }
        assumeTrue(disponivel, "MySQL fora do ar: testes pulados");
    }

    // Roda antes de cada teste: DAO novo e lista de criados vazia
    @BeforeEach
    void preparar() {
        dao = new UsuarioDao();
        criados.clear();
    }

    // Roda depois de cada teste: apaga do banco tudo o que o teste gravou.
    // Busca de novo pelo id para apagar a versão que está no banco
    @AfterEach
    void limpar() {
        for (Usuario u : criados) {
            if (u.getId() != null) {
                Usuario noBanco = dao.findById(u.getId());
                if (noBanco != null) {
                    dao.delete(noBanco);
                }
            }
        }
    }

    // Gera um texto único a cada chamada, para os testes não esbarrarem em dados antigos
    private String unico() {
        return String.valueOf(System.nanoTime());
    }

    // Monta um usuário válido, com login e CPF únicos (o CPF tem 11 dígitos)
    private Usuario novoUsuario() {
        String sufixo = unico();
        String cpf = String.format("%011d", Long.parseLong(sufixo) % 100000000000L);
        return new Usuario("Teste " + sufixo, cpf, "teste_" + sufixo, "Senha123");
    }

    // Grava pelo DAO e anota o usuário para ser apagado no fim do teste
    private Usuario gravar(Usuario u) {
        Usuario salvo = dao.save(u);
        criados.add(salvo);
        return salvo;
    }

    // Caminho feliz: serve de base para saber que o banco e o DAO funcionam
    @Test
    void saveGravaEGeraId() {
        Usuario salvo = gravar(novoUsuario());

        assertNotNull(salvo.getId(), "o banco deve gerar o id");
        assertNotNull(dao.findByLogin(salvo.getLogin()));
    }

    // Nome é obrigatório (nullable = false): o banco recusa e o DAO traduz o erro
    @Test
    void saveComNomeNuloLancaPersistenciaException() {
        Usuario invalido = novoUsuario();
        invalido.setNome(null);

        PersistenciaException erro = assertThrows(PersistenciaException.class,
                () -> dao.save(invalido));

        assertEquals("Erro ao salvar", erro.getMessage());
        assertNotNull(erro.getCause(), "a causa original deve ser preservada");
    }

    // CPF é único: o segundo usuário com o mesmo CPF é recusado pelo banco
    @Test
    void saveComCpfDuplicadoLancaPersistenciaException() {
        Usuario primeiro = gravar(novoUsuario());

        Usuario segundo = novoUsuario();
        segundo.setCpf(primeiro.getCpf()); // mesmo CPF, login diferente

        PersistenciaException erro = assertThrows(PersistenciaException.class,
                () -> dao.save(segundo));

        assertEquals("Erro ao salvar", erro.getMessage());
    }

    // Login repetido: a regra do UsuarioDao barra antes de tentar gravar
    @Test
    void saveComLoginDuplicadoLancaLoginDuplicadoException() {
        Usuario primeiro = gravar(novoUsuario());

        Usuario segundo = novoUsuario();
        segundo.setLogin(primeiro.getLogin()); // mesmo login, CPF diferente

        LoginDuplicadoException erro = assertThrows(LoginDuplicadoException.class,
                () -> dao.save(segundo));

        assertEquals(primeiro.getLogin(), erro.getLogin());
    }

    // Na edição, usar o login de OUTRO usuário também é recusado
    @Test
    void updateComLoginDeOutroUsuarioLancaLoginDuplicadoException() {
        Usuario primeiro = gravar(novoUsuario());
        Usuario segundo = gravar(novoUsuario());

        segundo.setLogin(primeiro.getLogin());

        assertThrows(LoginDuplicadoException.class, () -> dao.update(segundo));
    }

    // Na edição, manter o PRÓPRIO login não pode acusar duplicidade (não lança nada)
    @Test
    void updateMantendoOProprioLoginNaoLancaExcecao() {
        Usuario salvo = gravar(novoUsuario());

        salvo.setTelefone("74999990000");

        Usuario atualizado = dao.update(salvo);

        assertEquals("74999990000", atualizado.getTelefone());
    }

    // Na edição, CPF de outro usuário é recusado pelo banco e o DAO traduz o erro
    @Test
    void updateComCpfDeOutroUsuarioLancaPersistenciaException() {
        Usuario primeiro = gravar(novoUsuario());
        Usuario segundo = gravar(novoUsuario());

        segundo.setCpf(primeiro.getCpf());

        PersistenciaException erro = assertThrows(PersistenciaException.class,
                () -> dao.update(segundo));

        assertEquals("Erro ao atualizar", erro.getMessage());
    }
}
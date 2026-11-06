package br.com.ifba.usuario.repository;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Testa a regra "não podem existir dois usuários com o mesmo login".
// Estes testes usam o MySQL de verdade (o RepositorioUsuarioJPA real).
// Cada teste apaga no final os usuários que criou, para não deixar lixo no banco.
class RepositorioUsuarioJPATest {

    private RepositorioUsuarioJPA repositorio;

    // Usuários que os testes gravaram no banco, para apagar depois de cada teste
    private final List<Usuario> criados = new ArrayList<>();

    // Contador que ajuda a gerar CPFs e logins que nunca se repetem entre os testes
    private static long contador = 0;

    // Roda UMA vez antes de todos os testes: confere se o banco está acessível.
    // Se não estiver, os testes são IGNORADOS (aparecem como "skipped") em vez de falhar.
    @BeforeAll
    static void verificarBanco() {
        boolean bancoDisponivel;
        try {
            JPAUtil.getEntityManager().close(); // abre e fecha uma conexão de teste
            bancoDisponivel = true;
        } catch (Throwable erro) {
            bancoDisponivel = false;
        }
        Assumptions.assumeTrue(bancoDisponivel, "MySQL indisponível: testes ignorados");
    }

    // Roda antes de CADA teste: cria um repositório limpo
    @BeforeEach
    void preparar() {
        repositorio = new RepositorioUsuarioJPA();
    }

    // Roda depois de CADA teste: apaga do banco os usuários criados pelo teste
    @AfterEach
    void limparBanco() {
        for (Usuario usuario : criados) {
            if (usuario.getId() != null) {
                repositorio.excluir(usuario.getId());
            }
        }
        criados.clear();
    }

    // Monta um usuário de teste com o login recebido e um CPF único de 11 dígitos
    private Usuario novoUsuario(String login) {
        long unico = System.nanoTime() + contador++;
        String cpf = String.format("%011d", unico % 100_000_000_000L);
        return new Usuario("Teste JUnit", cpf, login, "senha123");
    }

    // Cadastra no banco e guarda na lista para o limpador apagar depois
    private void cadastrarERegistrar(Usuario usuario) {
        repositorio.cadastrar(usuario);
        criados.add(usuario);
    }

    // CASO DE ERRO: segundo cadastro com o mesmo login deve lançar a exceção
    @Test
    void cadastrarComLoginRepetidoDeveLancarLoginDuplicadoException() {
        String login = "teste_" + System.nanoTime();
        cadastrarERegistrar(novoUsuario(login));

        Usuario segundo = novoUsuario(login); // mesmo login, CPF diferente

        // assertThrows verifica que o código lança EXATAMENTE essa exceção
        // e devolve a exceção, para podermos conferir a mensagem dela
        LoginDuplicadoException erro = assertThrows(LoginDuplicadoException.class,
                () -> repositorio.cadastrar(segundo));

        assertTrue(erro.getMessage().contains(login)); // a mensagem cita o login repetido
        assertNull(segundo.getId()); // não chegou a ser gravado: continua sem id
    }

    // CAMINHO NORMAL: logins diferentes NÃO lançam exceção
    @Test
    void cadastrarComLoginsDiferentesNaoDeveLancarExcecao() {
        Usuario a = novoUsuario("teste_a_" + System.nanoTime());
        Usuario b = novoUsuario("teste_b_" + System.nanoTime());

        // assertDoesNotThrow falha o teste se o código lançar qualquer exceção
        assertDoesNotThrow(() -> cadastrarERegistrar(a));
        assertDoesNotThrow(() -> cadastrarERegistrar(b));
    }

    // CAMINHO NORMAL NA EDIÇÃO: o usuário mantém o próprio login, então não é "repetido"
    @Test
    void atualizarMantendoOProprioLoginNaoDeveLancarExcecao() {
        Usuario usuario = novoUsuario("teste_" + System.nanoTime());
        cadastrarERegistrar(usuario);

        usuario.setTelefone("11999999999"); // muda só o telefone

        assertDoesNotThrow(() -> repositorio.atualizar(usuario));
    }

    // CASO DE ERRO NA EDIÇÃO: trocar o login para o de OUTRO usuário deve lançar a exceção
    @Test
    void atualizarParaLoginDeOutroUsuarioDeveLancarExcecao() {
        String loginDoPrimeiro = "teste_" + System.nanoTime();
        Usuario primeiro = novoUsuario(loginDoPrimeiro);
        Usuario segundo = novoUsuario("teste_outro_" + System.nanoTime());
        cadastrarERegistrar(primeiro);
        cadastrarERegistrar(segundo);

        segundo.setLogin(loginDoPrimeiro); // tenta usar o login que já é do primeiro

        assertThrows(LoginDuplicadoException.class, () -> repositorio.atualizar(segundo));
    }
}
package br.com.ifba.usuario.repository;

import br.com.ifba.usuario.entity.Usuario;
// Exceção que o repositório agora lança quando o login procurado não existe
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
// @Test marca cada método como um teste que o JUnit executa sozinho
import org.junit.jupiter.api.Test;
// Traz assertEquals, assertTrue, assertSame, assertThrows etc.
import static org.junit.jupiter.api.Assertions.*;

// Testes unitários do repositório em memória e do equals/hashCode do Usuario
public class RepositorioUsuarioEmMemoriaTest {

    // Prova o equals: dois objetos DIFERENTES com o mesmo login são "iguais" para a lista
    @Test
    public void doisUsuariosDiferentesComMesmoLoginSaoIguaisParaALista() {
        // Mesmo login, mas nome, CPF e senha diferentes (são dois objetos distintos)
        Usuario a = new Usuario("Maria", "11111111111", "maria.santos", "senha123");
        Usuario b = new Usuario("Maria S.", "22222222222", "maria.santos", "outraSenha");

        List<Usuario> lista = new ArrayList<>();
        lista.add(a); // só o "a" entra na lista

        // contains usa o equals do Usuario: "b" nunca foi adicionado, mas tem o mesmo login
        assertTrue(lista.contains(b));
    }

    // Cadastrar um usuário e ele aparecer em listarTodos
    @Test
    public void cadastrarUsuarioDeveAparecerEmListarTodos() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario maria = new Usuario("Maria", "11111111111", "maria.santos", "senha123");

        repositorio.cadastrar(maria);

        // A lista deve ter exatamente 1 usuário, e ele deve ser a Maria
        assertEquals(1, repositorio.listarTodos().size());
        assertTrue(repositorio.listarTodos().contains(maria));
    }

    // CAMINHO NORMAL: cadastrar dois usuários e a busca por login devolver o certo.
    // Aqui a exceção NÃO é lançada, porque o login existe.
    @Test
    public void buscarPorLoginDeveDevolverOUsuarioCerto() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario maria = new Usuario("Maria", "11111111111", "maria.santos", "senha123");
        Usuario joao = new Usuario("João", "22222222222", "joao.silva", "senha456");
        repositorio.cadastrar(maria);
        repositorio.cadastrar(joao);

        // assertSame confere que é exatamente o mesmo objeto, não só um "igual"
        assertSame(joao, repositorio.buscarPorLogin("joao.silva"));
    }

    // CAMINHO NORMAL: a busca com for (percorrendo a lista) deve dar o mesmo resultado
    // que a busca com Map
    @Test
    public void buscarPorLoginComForDeveDevolverOMesmoResultado() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario maria = new Usuario("Maria", "11111111111", "maria.santos", "senha123");
        repositorio.cadastrar(maria);

        assertSame(maria, repositorio.buscarPorLoginComFor("maria.santos"));
    }

    // CASO DE ERRO (teste ALTERADO): buscar um login que não existe.
    // ANTES o método devolvia null, e o teste usava assertNull.
    // AGORA o método lança UsuarioNaoEncontradoException, então o teste usa assertThrows:
    // ele passa SE a exceção for lançada e FALHA se nada for lançado.
    @Test
    public void buscarPorLoginInexistenteDeveLancarUsuarioNaoEncontradoException() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        repositorio.cadastrar(new Usuario("Maria", "11111111111", "maria.santos", "senha123"));
    }
        // assertThrows executa o código que está depois da seta (->) e confere se ele lança
        // exatamente essa exceção. Também devolve a exceção, para podermos olhar
}
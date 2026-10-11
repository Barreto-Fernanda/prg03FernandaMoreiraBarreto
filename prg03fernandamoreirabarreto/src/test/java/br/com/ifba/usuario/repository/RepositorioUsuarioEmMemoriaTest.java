package br.com.ifba.usuario.repository;

import br.com.ifba.usuario.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
// @Test marca cada método como um teste que o JUnit executa sozinho
import org.junit.jupiter.api.Test;
// Traz assertEquals, assertTrue, assertSame, assertNull etc.
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

    // Cadastrar dois usuários e a busca por login devolver o certo
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

    // A busca com for (percorrendo a lista) deve dar o mesmo resultado que a busca com Map
    @Test
    public void buscarPorLoginComForDeveDevolverOMesmoResultado() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario maria = new Usuario("Maria", "11111111111", "maria.santos", "senha123");
        repositorio.cadastrar(maria);

        assertSame(maria, repositorio.buscarPorLoginComFor("maria.santos"));
    }

    // Buscar um login que não existe deve devolver null
    @Test
    public void buscarPorLoginInexistenteDeveDevolverNull() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        repositorio.cadastrar(new Usuario("Maria", "11111111111", "maria.santos", "senha123"));

        assertNull(repositorio.buscarPorLogin("nao.existe"));
    }

    // Regra do Java: se dois objetos são iguais (equals), o hashCode também deve ser igual
    @Test
    public void usuariosIguaisDevemTerMesmoHashCode() {
        Usuario a = new Usuario("Maria", "11111111111", "maria.santos", "senha123");
        Usuario b = new Usuario("Maria S.", "22222222222", "maria.santos", "outraSenha");

        assertEquals(a.hashCode(), b.hashCode());
    }
}
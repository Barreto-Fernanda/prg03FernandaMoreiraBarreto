package br.com.ifba.usuario.repository;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Guarda os usuários cadastrados em memória (os dados somem quando o programa fecha)
public class RepositorioUsuarioEmMemoria {

    // Lista com todos os usuários, na ordem em que foram cadastrados
    private final List<Usuario> usuarios = new ArrayList<>();

    // Índice: login -> Usuario (permite achar direto, sem percorrer a lista)
    private final Map<String, Usuario> porLogin = new HashMap<>();

    // Guarda o usuário na lista e também no índice por login
    public void cadastrar(Usuario usuario) {
        usuarios.add(usuario);
        porLogin.put(usuario.getLogin(), usuario); // alimenta o índice a cada cadastro
    }

    // Devolve uma CÓPIA da lista, para ninguém de fora mexer na lista original
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    // Versão 1: percorre a lista com for, usuário por usuário.
    // Se não achar, LANÇA UsuarioNaoEncontradoException.
    public Usuario buscarPorLoginComFor(String login) {
        for (Usuario usuario : usuarios) {
            if (Objects.equals(usuario.getLogin(), login)) {
                return usuario;
            }
        }
        // ANTES: "return null;". Quem chamasse e não verificasse o null só descobria
        // o problema mais tarde, com um NullPointerException em outro lugar do código.
        // AGORA: o erro aparece aqui, no ponto em que o problema é descoberto.
        throw new UsuarioNaoEncontradoException(login);
    }

    // Versão 2: vai direto pela chave, usando o Map.
    // Se a chave não existir, LANÇA UsuarioNaoEncontradoException.
    public Usuario buscarPorLogin(String login) {
        Usuario usuario = porLogin.get(login); // o Map devolve null se a chave não existe

        // ANTES: "return porLogin.get(login);" devolvia esse null direto para quem chamou.
        // AGORA: se veio null, transformamos o null em uma exceção com nome e mensagem claros.
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(login);
        }
        return usuario;
    }
}
package br.com.ifba.usuario.repository;

import br.com.ifba.usuario.entity.Usuario;
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

    // Versão 1: percorre a lista com for, usuário por usuário
    public Usuario buscarPorLoginComFor(String login) {
        for (Usuario usuario : usuarios) {
            if (Objects.equals(usuario.getLogin(), login)) {
                return usuario;
            }
        }
        return null; // não achou
    }

    // Versão 2: vai direto pela chave, usando o Map
    public Usuario buscarPorLogin(String login) {
        return porLogin.get(login); // devolve null se a chave não existe
    }
}
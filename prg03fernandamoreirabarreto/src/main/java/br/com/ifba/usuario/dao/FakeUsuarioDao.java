package br.com.ifba.usuario.dao;

// Entidade guardada na lista em memória
import br.com.ifba.usuario.entity.Usuario;
// Lista que faz o papel do banco
import java.util.ArrayList;
// Tipo da lista devolvida pelas buscas
import java.util.List;

// DAO de mentira (dublê): cumpre o mesmo contrato do UsuarioIDao, mas guarda os
// usuários numa List em memória. Sem conexão, sem transação, sem MySQL.
// Serve para testar as regras do Service sem depender do banco.
public class FakeUsuarioDao implements UsuarioIDao {

    // O "banco" de mentira
    private final List<Usuario> lista = new ArrayList<>();

    // Contador que imita o auto incremento do banco: cada save recebe um id novo
    private long proximoId = 1;

    @Override
    public Usuario save(Usuario usuario) {
        // O banco de verdade gera o id no INSERT; aqui o fake faz o mesmo
        usuario.setId(proximoId++);
        lista.add(usuario);
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        // Troca o usuário que tem o mesmo id pela versão nova
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(usuario.getId())) {
                lista.set(i, usuario);
                return usuario;
            }
        }
        return usuario;
    }

    @Override
    public void delete(Usuario usuario) {
        // Remove pelo id (o equals da Usuario compara por login, então não serve aqui)
        lista.removeIf(u -> u.getId().equals(usuario.getId()));
    }

    @Override
    public Usuario findById(Long id) {
        for (Usuario u : lista) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        // Devolve uma cópia, para quem chamou não mexer na lista interna
        return new ArrayList<>(lista);
    }

    @Override
    public Usuario findByLogin(String login) {
        for (Usuario u : lista) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        String termo = (texto == null) ? "" : texto.trim().toLowerCase();
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : lista) {
            if (u.getNome() != null && u.getNome().toLowerCase().contains(termo)) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }
}
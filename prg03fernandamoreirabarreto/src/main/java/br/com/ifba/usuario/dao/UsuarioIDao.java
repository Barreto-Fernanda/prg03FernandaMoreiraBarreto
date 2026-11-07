package br.com.ifba.usuario.dao;

// Contrato genérico do CRUD: a interface específica herda os cinco métodos dele
import br.com.ifba.infrastructure.dao.GenericIDao;
// Entidade com a qual este DAO trabalha
import br.com.ifba.usuario.entity.Usuario;

// Contrato do DAO de Usuario: o CRUD comum vem do GenericIDao<Usuario>,
// e aqui entra só o que é próprio do domínio Usuario
public interface UsuarioIDao extends GenericIDao<Usuario> {

    // Busca o usuário pelo login; devolve null se não existir nenhum com esse login
    Usuario findByLogin(String login);
}
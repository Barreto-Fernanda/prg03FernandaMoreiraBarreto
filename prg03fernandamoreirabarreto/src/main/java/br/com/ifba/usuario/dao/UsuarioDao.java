package br.com.ifba.usuario.dao;

// Classe genérica que já traz save, update, delete, findById e findAll prontos
import br.com.ifba.infrastructure.dao.GenericDao;
// Classe que entrega o EntityManager (a conexão de trabalho com o banco)
import br.com.ifba.infrastructure.util.JPAUtil;
// Entidade com a qual este DAO trabalha
import br.com.ifba.usuario.entity.Usuario;
// EntityManager: o objeto do JPA que executa as consultas
import jakarta.persistence.EntityManager;
// Tipo do resultado da consulta
import java.util.List;

// DAO de Usuario: herda todo o CRUD do GenericDao e cumpre o contrato do UsuarioIDao.
// Só precisa escrever o que é próprio do domínio: o findByLogin.
public class UsuarioDao extends GenericDao<Usuario> implements UsuarioIDao {

    // super(Usuario.class) diz ao GenericDao com qual entidade este DAO trabalha
    public UsuarioDao() {
        super(Usuario.class);
    }

    // Busca pelo login. @Override indica que cumpre o método do UsuarioIDao
    @Override
    public Usuario findByLogin(String login) {
        // Abre a conexão de trabalho com o banco
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // :login é um parâmetro, e o valor entra pelo setParameter (evita SQL injection)
            List<Usuario> resultado = em
                    .createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getResultList();
            // Lista vazia significa que não existe usuário com esse login
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            // Roda sempre: fecha a conexão
            em.close();
        }
    }
}
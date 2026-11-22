package br.com.ifba.usuario.dao;

// Classe genérica que já traz save, update, delete, findById e findAll prontos
import br.com.ifba.infrastructure.dao.GenericDao;
// Classe que entrega o EntityManager (a conexão de trabalho com o banco)
import br.com.ifba.infrastructure.util.JPAUtil;
// Entidade com a qual este DAO trabalha
import br.com.ifba.usuario.entity.Usuario;
// EntityManager: o objeto do JPA que executa as consultas
import jakarta.persistence.EntityManager;
// Tipo do resultado das consultas
import java.util.List;

// DAO de Usuario: herda todo o CRUD do GenericDao e cumpre o contrato do UsuarioIDao.
// O DAO não julga, só executa: as regras de negócio (como o login único)
// ficam no UsuarioService. Aqui só entram as buscas próprias do domínio Usuario.
public class UsuarioDao extends GenericDao<Usuario> implements UsuarioIDao {

    // super(Usuario.class) diz ao GenericDao com qual entidade este DAO trabalha
    public UsuarioDao() {
        super(Usuario.class);
    }

    // Busca pelo login; devolve null se não existir
    @Override
    public Usuario findByLogin(String login) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // :login é um parâmetro: o valor entra separado da consulta (evita SQL injection)
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

    // Busca os usuários cujo nome contém o texto, sem diferenciar maiúsculas de minúsculas
    @Override
    public List<Usuario> findByNome(String texto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Se vier null, trata como vazio; trim() tira os espaços do começo e do fim
            String termo = (texto == null) ? "" : texto.trim().toLowerCase();

            // LOWER deixa o nome em minúsculas antes de comparar; LIKE com % dos dois
            // lados significa "contém o texto em qualquer posição"
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE :texto ORDER BY u.nome",
                    Usuario.class)
                    .setParameter("texto", "%" + termo + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
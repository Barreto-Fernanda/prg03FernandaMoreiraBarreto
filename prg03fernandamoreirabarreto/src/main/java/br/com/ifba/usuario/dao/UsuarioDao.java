package br.com.ifba.usuario.dao;

// Classe genérica que já traz save, update, delete, findById e findAll prontos
import br.com.ifba.infrastructure.dao.GenericDao;
// Classe que entrega o EntityManager (a conexão de trabalho com o banco)
import br.com.ifba.infrastructure.util.JPAUtil;
// Entidade com a qual este DAO trabalha
import br.com.ifba.usuario.entity.Usuario;
// Exceção de domínio lançada quando o login já pertence a outro usuário
import br.com.ifba.usuario.exception.LoginDuplicadoException;
// EntityManager: o objeto do JPA que executa as consultas
import jakarta.persistence.EntityManager;
// Tipo do resultado das consultas
import java.util.List;

// DAO de Usuario: herda todo o CRUD do GenericDao e cumpre o contrato do UsuarioIDao.
// Escreve só o que é próprio do domínio: busca por login, busca por nome e a regra do login único.
public class UsuarioDao extends GenericDao<Usuario> implements UsuarioIDao {

    // super(Usuario.class) diz ao GenericDao com qual entidade este DAO trabalha
    public UsuarioDao() {
        super(Usuario.class);
    }

    // Grava um usuário novo, mas antes confere se o login está livre.
    // Se não estiver, a LoginDuplicadoException sai daqui e nada é gravado.
    @Override
    public Usuario save(Usuario usuario) {
        garantirLoginDisponivel(usuario);
        // super.save é o save do GenericDao (transação, rollback, close)
        return super.save(usuario);
    }

    // Atualiza um usuário, conferindo o login antes (ignorando o próprio usuário)
    @Override
    public Usuario update(Usuario usuario) {
        garantirLoginDisponivel(usuario);
        return super.update(usuario);
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
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
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

    // Confere se o login do usuário está livre. Se não estiver, LANÇA a exceção.
    // Mesma regra que existia no RepositorioUsuarioJPA, agora no lugar certo.
    private void garantirLoginDisponivel(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long quantidade;

            if (usuario.getId() == null) {
                // Usuário novo (sem id): conta quantos já usam este login
                quantidade = em.createQuery(
                        "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login", Long.class)
                        .setParameter("login", usuario.getLogin())
                        .getSingleResult();
            } else {
                // Usuário em edição: conta só os OUTROS (id diferente) com este login,
                // para ele não ser acusado de repetir o próprio login
                quantidade = em.createQuery(
                        "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login AND u.id <> :id", Long.class)
                        .setParameter("login", usuario.getLogin())
                        .setParameter("id", usuario.getId())
                        .getSingleResult();
            }

            // Se achou pelo menos um, o login já está em uso
            if (quantidade > 0) {
                throw new LoginDuplicadoException(usuario.getLogin());
            }
        } finally {
            em.close();
        }
    }
}
package br.com.ifba.usuario.repository;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.EntityManager;
import java.util.List;

// Grava e busca usuários no MySQL usando JPA
public class RepositorioUsuarioJPA {

    // CADASTRAR: persist dentro de uma transação
    public void cadastrar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            // Se deu erro com a transação aberta, desfaz tudo
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // quem chamou (a tela) decide o que mostrar
        } finally {
            em.close(); // sempre fecha, deu certo ou não
        }
    }

    // LISTAR: leitura não precisa de transação
    // Em JPQL se usa o nome da classe (Usuario), não o da tabela (usuario)
    public List<Usuario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u ORDER BY u.nome", Usuario.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // EDITAR: merge dentro de uma transação
    public Usuario atualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario atualizado = em.merge(usuario);
            em.getTransaction().commit();
            return atualizado;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // EXCLUIR: o remove só aceita um objeto que o EntityManager conhece,
    // por isso primeiro busca pelo id (find) e depois remove
    public void excluir(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
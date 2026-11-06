package br.com.ifba.usuario.repository;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import jakarta.persistence.EntityManager;
import java.util.List;

// Grava e busca usuários no MySQL usando JPA
public class RepositorioUsuarioJPA {

    // CADASTRAR: confere o login, depois persist dentro de uma transação
    public void cadastrar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Se o login já existir, este método lança a exceção e o código para aqui,
            // antes de abrir a transação e antes de tentar gravar qualquer coisa
            garantirLoginDisponivel(em, usuario);

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

    // EDITAR: confere o login, depois merge dentro de uma transação
    
        // BUSCAR: devolve os usuários cujo nome CONTÉM o texto digitado.
    // Não diferencia maiúsculas de minúsculas ("ana" encontra "Ana Souza" e "MARIANA").
    public List<Usuario> buscarPorNome(String texto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Se vier null, trata como texto vazio; trim() tira espaços do começo e do fim
            // (o campo de busca da tela tem espaços em branco por padrão)
            String termo = (texto == null) ? "" : texto.trim().toLowerCase();

            // WHERE filtra no próprio banco: só voltam os usuários que combinam.
            // LOWER(u.nome) deixa o nome em minúsculas antes de comparar.
            // LIKE com % dos dois lados significa "contém o texto em qualquer posição".
            // :texto é um PARÂMETRO: o valor entra separado da consulta, e nunca é colado
            // dentro do texto da consulta. Isso evita SQL injection.
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE :texto ORDER BY u.nome",
                    Usuario.class)
                    .setParameter("texto", "%" + termo + "%")
                    .getResultList();
        } finally {
            em.close(); // sempre fecha, deu certo ou não
        }
    }
    
    public Usuario atualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Na edição, o login só é "repetido" se pertencer a OUTRO usuário
            garantirLoginDisponivel(em, usuario);

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

    // Confere se o login do usuário está livre. Se não estiver, LANÇA a exceção.
    // Em vez de devolver true/false (e depender de quem chama lembrar de verificar),
    // o método simplesmente não deixa o código continuar quando há problema.
    private void garantirLoginDisponivel(EntityManager em, Usuario usuario) {
        Long quantidade;

        if (usuario.getId() == null) {
            // Usuário novo (ainda sem id): conta quantos usuários já usam este login
            quantidade = em.createQuery(
                    "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login", Long.class)
                    .setParameter("login", usuario.getLogin())
                    .getSingleResult();
        } else {
            // Usuário em edição: conta os OUTROS usuários (id diferente) com este login,
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
    }
}
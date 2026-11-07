package br.com.ifba.infrastructure.dao;

// Superclasse de todas as entidades: é ela que limita quais tipos este DAO aceita
import br.com.ifba.infrastructure.entity.PersistenceEntity;
// Exceção de domínio que o DAO lança quando uma operação de persistência falha
import br.com.ifba.infrastructure.exception.PersistenciaException;
// Classe que entrega o EntityManager (a conexão de trabalho com o banco)
import br.com.ifba.infrastructure.util.JPAUtil;
// EntityManager: o objeto do JPA que faz persist, merge, remove e find
import jakarta.persistence.EntityManager;
// Tipo da lista que o método findAll devolve
import java.util.List;

// Implementa o contrato GenericIDao: aqui está o COMO de cada operação.
// <Entity extends PersistenceEntity> significa: serve para qualquer entidade do
// projeto, desde que ela herde de PersistenceEntity.
public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {

    // Guarda com qual entidade este DAO trabalha (Usuario.class, por exemplo).
    // Quem cria o DAO diz qual é; sem isso ele não saberia montar a consulta nem chamar o find
    private final Class<Entity> tipo;

    // Construtor: recebe o tipo da entidade e guarda no atributo acima
    public GenericDao(Class<Entity> tipo) {
        this.tipo = tipo;
    }

    // Grava uma entidade nova. @Override indica que cumpre o método do contrato
    @Override
    public Entity save(Entity entity) {
        // Abre a conexão de trabalho com o banco
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Começa a transação (tudo ou nada)
            em.getTransaction().begin();
            // Marca a entidade para ser inserida no banco
            em.persist(entity);
            // Confirma: é aqui que o INSERT acontece de verdade
            em.getTransaction().commit();
            // Devolve a entidade já com o id gerado pelo banco
            return entity;
        } catch (Exception e) {
            // Desfaz a transação, mas só se ela chegou a ser aberta
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Troca o erro técnico pela exceção de domínio, guardando a causa original
            throw new PersistenciaException("Erro ao salvar", e);
        } finally {
            // Roda sempre, com erro ou sem erro: fecha a conexão
            em.close();
        }
    }

    // Atualiza uma entidade que já existe e devolve a versão atualizada
    @Override
    public Entity update(Entity entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // merge copia os dados da entidade para o registro que já existe no banco
            Entity atualizada = em.merge(entity);
            em.getTransaction().commit();
            return atualizada;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Erro ao atualizar", e);
        } finally {
            em.close();
        }
    }

    // Remove a entidade do banco (não devolve nada)
    @Override
    public void delete(Entity entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // O remove só aceita entidade "gerenciada" por este EntityManager.
            // Se ela veio de fora, o merge traz ela para dentro antes de remover
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Erro ao excluir", e);
        } finally {
            em.close();
        }
    }

    // Busca uma entidade pelo id; devolve null se não existir nenhuma com esse id.
    // É só leitura: não precisa de transação nem de rollback, mas precisa do close
    @Override
    public Entity findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // find usa o tipo guardado no construtor para saber em qual tabela procurar
            return em.find(tipo, id);
        } finally {
            em.close();
        }
    }

    // Devolve a lista com todas as entidades gravadas
    @Override
    public List<Entity> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // O nome da classe vem do tipo, nunca do usuário (por isso é seguro concatenar).
            // Para Usuario, a consulta vira: SELECT e FROM Usuario e
            String jpql = "SELECT e FROM " + tipo.getSimpleName() + " e";
            // Executa a consulta e devolve o resultado como lista
            return em.createQuery(jpql, tipo).getResultList();
        } finally {
            em.close();
        }
    }
}
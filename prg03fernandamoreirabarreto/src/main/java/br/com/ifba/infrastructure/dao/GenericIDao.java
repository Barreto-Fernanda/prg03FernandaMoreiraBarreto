package br.com.ifba.infrastructure.dao;

// Superclasse de todas as entidades: é ela que limita quais tipos o DAO aceita
import br.com.ifba.infrastructure.entity.PersistenceEntity;
// Tipo da lista que o método findAll devolve
import java.util.List;

// O CONTRATO de todo DAO: declara o que ele precisa saber fazer, e nada sobre COMO.
// Só assinaturas, nenhum corpo de método.
//
// <Entity extends PersistenceEntity> é um tipo GENÉRICO: "Entity" é um nome que vale
// pela entidade com a qual o DAO vai trabalhar (Usuario, por exemplo).
// O "extends PersistenceEntity" obriga que essa entidade herde da PersistenceEntity,
// então o contrato funciona para qualquer entidade do projeto, e só para elas.
public interface GenericIDao<Entity extends PersistenceEntity> {

    // Grava uma entidade nova e devolve a entidade gravada (já com o id gerado pelo banco)
    Entity save(Entity entity);

    // Atualiza uma entidade que já existe e devolve a versão atualizada
    Entity update(Entity entity);

    // Remove a entidade do banco (não devolve nada)
    void delete(Entity entity);

    // Busca uma entidade pelo id; devolve null se não existir nenhuma com esse id
    Entity findById(Long id);

    // Devolve a lista com todas as entidades gravadas
    List<Entity> findAll();
}
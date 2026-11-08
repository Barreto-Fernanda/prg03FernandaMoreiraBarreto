package br.com.ifba.infrastructure.entity;

// Anotação que marca o atributo como a CHAVE PRIMÁRIA da tabela
import jakarta.persistence.Id;
// Anotação que diz que o valor da chave é GERADO pelo banco, e não digitado por nós
import jakarta.persistence.GeneratedValue;
// Lista as estratégias de geração do valor (AUTO, IDENTITY, SEQUENCE...); usamos a IDENTITY
import jakarta.persistence.GenerationType;
// Anotação que marca a classe como "superclasse mapeada": ela não vira tabela,
// mas os atributos dela entram nas tabelas das classes filhas
import jakarta.persistence.MappedSuperclass;

// Superclasse de todas as entidades do projeto: guarda só o que TODAS têm em comum, o id.
// @MappedSuperclass diz à JPA: "esta classe não vira uma tabela, mas os atributos dela
// entram nas tabelas das classes que herdam dela". Assim o id fica escrito uma única vez.
// É abstract porque não faz sentido criar um objeto "PersistenceEntity" sozinho.
@MappedSuperclass
public abstract class PersistenceEntity {

    // Chave primária. O banco gera o valor sozinho (auto incremento do MySQL).
    // continua IDENTITY, como já era na Usuario. Se trocáss para AUTO,
    // o Hibernate passaria a exigir uma tabela de sequência que as migrations
    // do Flyway não criaram, e o hbm2ddl.auto=validate recusaria subir a aplicação.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem herda de PersistenceEntity ganha estes dois métodos de graça
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
package br.com.ifba.infrastructure.util;

// Canal de trabalho com o banco, entregue a quem pede
import jakarta.persistence.EntityManager;
// Fábrica de EntityManager (objeto pesado, criado uma única vez)
import jakarta.persistence.EntityManagerFactory;
// Cria a fábrica a partir do nome da persistence-unit
import jakarta.persistence.Persistence;
// Mapa chave/valor com as configurações que vamos entregar ao Hibernate
import java.util.HashMap;
import java.util.Map;
// Configurações lidas do application.properties
import java.util.Properties;

public class JPAUtil {

    // Nome exato da persistence-unit do persistence.xml
    private static final String PERSISTENCE_UNIT = "prg03PU";

    // Prefixo das chaves do Hibernate dentro do application.properties
    private static final String PREFIXO_HIBERNATE = "spring.jpa.properties.";

    // static final: a fábrica é criada uma única vez, quando a classe é carregada
    private static final EntityManagerFactory FACTORY = criarFabrica();

    // Construtor privado: ninguém precisa instanciar o JPAUtil
    private JPAUtil() {
    }

    // Monta as configurações a partir do application.properties e cria a fábrica.
    // O que for passado aqui tem prioridade sobre o persistence.xml.
    private static EntityManagerFactory criarFabrica() {
        Properties arquivo = ConfiguracaoBanco.carregar();
        Map<String, Object> config = new HashMap<>();

        // Conexão: troca os nomes do Spring pelos nomes que a JPA entende
        config.put("jakarta.persistence.jdbc.url", arquivo.getProperty("spring.datasource.url"));
        config.put("jakarta.persistence.jdbc.user", arquivo.getProperty("spring.datasource.username"));
        config.put("jakarta.persistence.jdbc.password", arquivo.getProperty("spring.datasource.password"));
        config.put("jakarta.persistence.jdbc.driver", arquivo.getProperty("spring.datasource.driver-class-name"));

        // Propriedades do Hibernate: tira o prefixo "spring.jpa.properties."
        // (ex.: spring.jpa.properties.hibernate.dialect vira hibernate.dialect)
        for (String chave : arquivo.stringPropertyNames()) {
            if (chave.startsWith(PREFIXO_HIBERNATE)) {
                config.put(chave.substring(PREFIXO_HIBERNATE.length()), arquivo.getProperty(chave));
            }
        }

        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, config);
    }

    // Cada chamada devolve um EntityManager novo, que quem pediu deve fechar
    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    // Para chamar ao encerrar a aplicação
    public static void fechar() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}
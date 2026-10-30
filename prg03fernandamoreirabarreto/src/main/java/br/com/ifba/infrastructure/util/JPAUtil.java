package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    // Nome exato da persistence-unit do persistence.xml
    private static final String PERSISTENCE_UNIT = "prg03PU";

    // static final: a fábrica é criada uma única vez, quando a classe é carregada
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    // Construtor privado: ninguém precisa instanciar o JPAUtil
    private JPAUtil() {
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

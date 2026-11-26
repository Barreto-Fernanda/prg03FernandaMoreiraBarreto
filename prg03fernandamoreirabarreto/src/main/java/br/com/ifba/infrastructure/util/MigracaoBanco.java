package br.com.ifba.infrastructure.util;

// Ferramenta que aplica as migrations (V1, V2... da pasta db/migration) no banco
import org.flywaydb.core.Flyway;
// Configurações lidas do application.properties
import java.util.Properties;

// Aplica as migrations pendentes
public class MigracaoBanco {

    public static void aplicar() {
        // Mesma fonte de configuração do Hibernate: o application.properties
        Properties config = ConfiguracaoBanco.carregar();

        // Liga o Flyway ao banco e aplica só o que ainda não foi aplicado
        Flyway flyway = Flyway.configure()
                .dataSource(config.getProperty("spring.datasource.url"),
                            config.getProperty("spring.datasource.username"),
                            config.getProperty("spring.datasource.password"))
                .load();
        flyway.migrate();
    }
}
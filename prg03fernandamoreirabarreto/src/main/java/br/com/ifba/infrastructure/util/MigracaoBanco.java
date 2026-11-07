package br.com.ifba.infrastructure.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.flywaydb.core.Flyway;

// Aplica as migrations pendentes (arquivos V1, V2... da pasta db/migration)
public class MigracaoBanco {

    public static void aplicar() {
        Properties config = new Properties();

        // Lê url, usuário e senha do banco.properties (arquivo local, fora do Git)
        try (InputStream entrada = MigracaoBanco.class.getResourceAsStream("/banco.properties")) {
            if (entrada == null) {
                throw new IllegalStateException("banco.properties nao encontrado em src/main/resources");
            }
            config.load(entrada);
        } catch (IOException e) {
            throw new IllegalStateException("Nao foi possivel ler o banco.properties", e);
        }

        // Liga o Flyway ao banco e aplica só o que ainda não foi aplicado
        Flyway flyway = Flyway.configure()
                .dataSource(config.getProperty("db.url"),
                            config.getProperty("db.user"),
                            config.getProperty("db.password"))
                .load();
        flyway.migrate();
    }
}
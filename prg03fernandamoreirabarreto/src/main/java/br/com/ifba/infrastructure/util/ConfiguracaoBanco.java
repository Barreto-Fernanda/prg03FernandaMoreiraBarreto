package br.com.ifba.infrastructure.util;

// Lê o arquivo de configuração (fluxo de bytes que vem de dentro do projeto)
import java.io.InputStream;
// Erro de leitura de arquivo, que precisamos tratar
import java.io.IOException;
// Estrutura chave=valor do Java, exatamente o formato do application.properties
import java.util.Properties;

// Lê o application.properties UMA vez e entrega os valores a quem precisar
// (o JPAUtil, para o Hibernate, e o MigracaoBanco, para o Flyway).
// Assim a senha e a URL do banco ficam escritas em um lugar só.
public final class ConfiguracaoBanco {

    // Caminho do arquivo dentro de src/main/resources (a barra inicial significa "raiz")
    private static final String ARQUIVO = "/application.properties";

    // Construtor privado: ninguém precisa criar objeto desta classe, só usar o método abaixo
    private ConfiguracaoBanco() {
    }

    // Carrega o arquivo e devolve todas as chaves e valores
    public static Properties carregar() {
        Properties config = new Properties();

        // try-with-resources: fecha o arquivo sozinho no final, deu certo ou não
        try (InputStream entrada = ConfiguracaoBanco.class.getResourceAsStream(ARQUIVO)) {
            // Se o arquivo não existe (ex.: outro computador sem o arquivo real), avisa com clareza
            if (entrada == null) {
                throw new IllegalStateException(
                        "application.properties nao encontrado em src/main/resources. "
                        + "Copie o application.properties.exemplo e preencha a senha.");
            }
            config.load(entrada); // lê as linhas chave=valor para dentro do objeto
        } catch (IOException e) {
            // Guarda o erro original como causa, para não perder a informação técnica
            throw new IllegalStateException("Nao foi possivel ler o application.properties", e);
        }
        return config;
    }
}
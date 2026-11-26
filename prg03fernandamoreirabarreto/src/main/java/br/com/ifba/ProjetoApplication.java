package br.com.ifba;

// Classe da tela de login: é ela que o Spring entrega pronta, já com controller, service e dao dentro
import br.com.ifba.login.view.TelaLogin;
// Aplica as migrations do Flyway no banco (cria/atualiza as tabelas) antes de o sistema usar o banco
import br.com.ifba.infrastructure.util.MigracaoBanco;
// Anotação que marca esta classe como a ENTRADA do Spring Boot e manda varrer os pacotes
// a partir de br.com.ifba procurando classes anotadas
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Montador que permite configurar o Spring antes de iniciar (usamos para o headless)
import org.springframework.boot.builder.SpringApplicationBuilder;
// O contêiner do Spring depois de iniciado: é onde os objetos (beans) ficam guardados
import org.springframework.context.ConfigurableApplicationContext;

// Classe de entrada do projeto: substitui o Main antigo que dava new em DAO, Service e Controller.
// Nenhum new de DAO, Service ou Controller aparece aqui: quem monta tudo é o Spring.
@SpringBootApplication
public class ProjetoApplication {

    public static void main(String[] args) {

        // Mantém o que o Main antigo já fazia: o banco precisa estar migrado ANTES de a
        // primeira tela abrir, senão o hbm2ddl.auto=validate reclama das tabelas
        MigracaoBanco.aplicar();

        // Sobe o contêiner do Spring: ele varre as classes anotadas, cria os objetos
        // e entrega cada um a quem declarou precisar dele no construtor.
        // headless(false) é obrigatório em aplicação desktop: avisa ao Spring que haverá
        // interface gráfica. Sem isso, ele lança exceção ao subir.
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(ProjetoApplication.class)
                        .headless(false)
                        .run(args);

        // Pede ao contêiner a tela de login, que já vem montada com tudo dentro
        TelaLogin tela = context.getBean(TelaLogin.class);
        tela.setVisible(true);
    }
}
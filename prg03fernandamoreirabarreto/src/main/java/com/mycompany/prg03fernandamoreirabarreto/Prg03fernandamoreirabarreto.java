package com.mycompany.prg03fernandamoreirabarreto;

// Aplica as migrations do Flyway antes de a aplicação subir
import br.com.ifba.infrastructure.util.MigracaoBanco;
// Tela inicial: ela recebe o Controller pelo construtor
import br.com.ifba.login.view.TelaLogin;
// Controller: interface (o que a tela conhece) e classe (a implementação)
import br.com.ifba.usuario.controller.UsuarioController;
import br.com.ifba.usuario.controller.UsuarioIController;
// DAO: interface e classe que fala com o MySQL
import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.dao.UsuarioIDao;
// Service: interface e classe com as regras de negócio
import br.com.ifba.usuario.service.UsuarioIService;
import br.com.ifba.usuario.service.UsuarioService;

public class Prg03fernandamoreirabarreto {

    public static void main(String[] args) {
        MigracaoBanco.aplicar();

        java.awt.EventQueue.invokeLater(() -> {
            UsuarioIDao dao = new UsuarioDao();
            UsuarioIService service = new UsuarioService(dao);
            UsuarioIController controller = new UsuarioController(service);

            new TelaLogin(controller).setVisible(true);
        });
    }
}
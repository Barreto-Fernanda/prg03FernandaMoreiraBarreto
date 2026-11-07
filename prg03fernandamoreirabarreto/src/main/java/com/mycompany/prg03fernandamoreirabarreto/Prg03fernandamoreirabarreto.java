package com.mycompany.prg03fernandamoreirabarreto;

public class Prg03fernandamoreirabarreto {

    public static void main(String[] args) {
        // 1º: aplica as migrations pendentes
        br.com.ifba.infrastructure.util.MigracaoBanco.aplicar();

        // 2º: só depois a aplicação sobe
        java.awt.EventQueue.invokeLater(() -> {
            new br.com.ifba.login.view.TelaLogin().setVisible(true);
        });
    }
}
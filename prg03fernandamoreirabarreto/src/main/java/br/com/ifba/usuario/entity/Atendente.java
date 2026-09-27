package br.com.ifba.usuario.entity;

// É um Usuario especializado: responsável pelo cadastro de amostras e pacientes
public class Atendente extends Usuario {

    public Atendente(String nome, String cpf, String login, String senha) {
        super(nome, cpf, login, senha);
    }

    @Override
    public String descricaoFuncao() {
        return "Atendente responsável pelo cadastro de amostras e pacientes";
    }
}
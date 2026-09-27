package br.com.ifba.usuario.entity;

// É um Usuario especializado: responsável por conduzir a análise e avançar o status
public class TecnicoLaboratorio extends Usuario {

    public TecnicoLaboratorio(String nome, String cpf, String login, String senha) {
        super(nome, cpf, login, senha);
    }

    @Override
    public String descricaoFuncao() {
        return "Técnico de Laboratório responsável pela análise e status das amostras";
    }
}
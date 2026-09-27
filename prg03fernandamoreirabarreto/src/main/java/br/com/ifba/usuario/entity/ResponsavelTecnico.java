package br.com.ifba.usuario.entity;

// É um Usuario especializado: responsável pela emissão e liberação do laudo
public class ResponsavelTecnico extends Usuario {

    public ResponsavelTecnico(String nome, String cpf, String login, String senha) {
        super(nome, cpf, login, senha);
    }

    @Override
    public String descricaoFuncao() {
        return "Responsável Técnico responsável pela emissão do laudo";
    }
}

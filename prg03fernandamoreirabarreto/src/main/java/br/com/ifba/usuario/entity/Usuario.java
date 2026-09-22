package br.com.ifba.usuario.entity;
import br.com.ifba.usuario.interfaces.Autenticavel;

// Classe de domínio: representa um usuário do sistema.
// Atributos privados (encapsulados) — acesso só via getters/setters.
public class Usuario implements Autenticavel {

    private String nome;
    private String cpf;
    private String genero;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String login;
    private String senha;

    // Construtor vazio: cria um Usuario "em branco", pra preencher depois
    public Usuario() {
    }

    // Construtor com os atributos principais
    public Usuario(String nome, String cpf, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
    }

    // Getters e setters de cada atributo
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // compara os parâmetros recebidos
    // com os atributos privados do próprio objeto (this.login, this.senha)
    @Override
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }
}
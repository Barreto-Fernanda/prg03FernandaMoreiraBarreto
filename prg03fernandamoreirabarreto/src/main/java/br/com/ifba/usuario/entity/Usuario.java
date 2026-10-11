package br.com.ifba.usuario.entity;

// Contrato que obriga a classe a ter o método autenticar(login, senha)
import br.com.ifba.usuario.interfaces.Autenticavel;
// Usado no equals/hashCode: compara e calcula hash sem estourar erro se o valor for null
import java.util.Objects;

// Classe de domínio: representa um usuário do sistema.
// Atributos privados (encapsulados): acesso só via getters/setters.
// Atendente, TecnicoLaboratorio e ResponsavelTecnico herdam desta classe.
public class Usuario implements Autenticavel {

    // Dados pessoais e de acesso do usuário (privados, ninguém de fora mexe direto)
    private String nome;
    private String cpf;
    private String genero;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String login;
    private String senha;

    // Construtor vazio: cria um Usuario "em branco", para preencher depois com os setters
    public Usuario() {
    }

    // Construtor com os dados principais: já cria o usuário preenchido
    // (sobrecarga: mesmo nome do construtor acima, parâmetros diferentes)
    public Usuario(String nome, String cpf, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
    }

    // Getters (devolvem o valor) e setters (guardam um novo valor) de cada atributo
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    // Cumpre o contrato da interface Autenticavel: compara o que foi digitado
    // (parâmetros) com o que está guardado neste objeto (this.login, this.senha)
    @Override
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    // Descreve a função do usuário no sistema; cada tipo de usuário sobrescreve (polimorfismo)
    public String descricaoFuncao() {
        return "Usuário do sistema";
    }

    // Dois usuários são iguais se tiverem o mesmo login: o login identifica o usuário
    // no sistema (é único e é a chave de busca do repositório).
    @Override
    public boolean equals(Object o) {
        // Atalho: se for exatamente o mesmo objeto na memória, já é igual
        if (this == o) {
            return true; // é o mesmo objeto
        }
        // Se não for um Usuario (ou for null), não pode ser igual
        if (!(o instanceof Usuario)) {
            return false; // não é um Usuario (ou é null)
        }
        // Converte para Usuario (seguro: o instanceof acima já garantiu o tipo)
        Usuario outro = (Usuario) o;
        // Só o login decide a igualdade; os outros campos são ignorados
        return Objects.equals(this.login, outro.login);
    }

    // Regra do Java: objetos iguais precisam ter o mesmo hashCode (use o mesmo campo)
    // Coleções como HashMap usam o hashCode para achar rápido onde o objeto está guardado
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
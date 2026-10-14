package br.com.ifba.usuario.entity;

import br.com.ifba.usuario.interfaces.Autenticavel;
// Anotações da JPA (pacote jakarta, e não javax)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

// Classe de domínio: representa um usuário do sistema.
// Atributos privados (encapsulados): acesso só via getters/setters.
// @Entity: o Hibernate passa a tratar esta classe como uma tabela do banco.
// @Table: nome da tabela (sem isso seria "Usuario", o nome da classe).
@Entity
@Table(name = "usuario")
public class Usuario implements Autenticavel {

    // Chave primária. O banco gera o valor sozinho (auto incremento no MySQL).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable = false: a coluna não aceita valor vazio (NULL)
    @Column(nullable = false, length = 120)
    private String nome;

    // unique = true: o banco impede dois usuários com o mesmo CPF
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    private String genero;

    // name: nome da coluna no banco (sem isso seria "dataNascimento")
    @Column(name = "data_nascimento")
    private String dataNascimento;

    private String telefone;
    private String email;

    // O login identifica o usuário no sistema, então também é único
    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(nullable = false)
    private String senha;

    // Construtor vazio: a JPA EXIGE um (ela cria o objeto vazio e depois preenche)
    public Usuario() {
    }

    public Usuario(String nome, String cpf, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario outro = (Usuario) o;
        return Objects.equals(this.login, outro.login);
    }

    // Objetos iguais precisam ter o mesmo hashCode (usa o mesmo campo do equals)
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
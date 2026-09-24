
package br.com.ifba.amostra.entity;

public class Exame {
    private String nome;

    public Exame() {
    }

    public Exame(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

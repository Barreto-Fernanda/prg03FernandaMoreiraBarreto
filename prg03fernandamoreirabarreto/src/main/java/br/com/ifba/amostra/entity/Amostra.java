package br.com.ifba.amostra.entity;

import br.com.ifba.amostra.enums.StatusAmostra;
import java.util.ArrayList;
import java.util.List;

public class Amostra {

    private String tipo;
    private Paciente paciente;       // objeto como atributo (1 amostra -> 1 paciente)
    private List<Exame> exames;      // relacionamento 1..N
    private StatusAmostra status;

    public Amostra(String tipo, Paciente paciente) {
        this.tipo = tipo;
        this.paciente = paciente;
        this.exames = new ArrayList<>();
        this.status = StatusAmostra.RECEBIDA; // RF02 status inicial automático
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public StatusAmostra getStatus() {
        return status;
    }

    public void setStatus(StatusAmostra status) {
        this.status = status;
    }

    // Devolve uma copia da lista, quem chama não consegue mexer na lista original
    public List<Exame> getExames() {
        return new ArrayList<>(exames);
    }

    // único jeito de colocar um exame na amostra
    public void adicionarExame(Exame exame) {
        exames.add(exame);
    }
}

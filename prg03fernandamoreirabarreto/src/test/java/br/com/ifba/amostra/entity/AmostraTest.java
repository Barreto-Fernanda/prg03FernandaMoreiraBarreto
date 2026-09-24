package br.com.ifba.amostra.entity;

import br.com.ifba.amostra.enums.StatusAmostra;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AmostraTest {

    @Test
    public void amostraDeveNascerComStatusRecebida() {
        Paciente paciente = new Paciente("Maria", "12345678900");
        Amostra amostra = new Amostra("Sangue", paciente);

        assertEquals(StatusAmostra.RECEBIDA, amostra.getStatus());
    }

    @Test
    public void adicionarExameDeveFazerListaCrescer() {
        Paciente paciente = new Paciente("Maria", "12345678900");
        Amostra amostra = new Amostra("Sangue", paciente);

        assertEquals(0, amostra.getExames().size());

        amostra.adicionarExame(new Exame("Hemograma"));

        assertEquals(1, amostra.getExames().size());
    }

    @Test
    public void getPacienteDeveDevolverOPacienteAssociado() {
        Paciente paciente = new Paciente("Maria", "12345678900");
        Amostra amostra = new Amostra("Sangue", paciente);

        assertEquals("Maria", amostra.getPaciente().getNome());
    }
}

package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioHierarquiaTest {

    // comportamento HERDADO (a filha usa o método da mãe sem reescrever)

    @Test
    public void atendenteDeveAutenticarUsandoMetodoHerdadoDaMae() {
        Atendente atendente = new Atendente("Maria", "12345678900", "maria.atendente", "senha123");

        assertTrue(atendente.autenticar("maria.atendente", "senha123"));
    }

    // comportamento SOBRESCRITO (cada filha devolve seu próprio resultado) 

    @Test
    public void atendenteDeveDevolverDescricaoPropria() {
        Atendente atendente = new Atendente("Maria", "12345678900", "maria.atendente", "senha123");

        assertEquals("Atendente responsável pelo cadastro de amostras e pacientes",
                atendente.descricaoFuncao());
    }

    @Test
    public void tecnicoLaboratorioDeveDevolverDescricaoPropria() {
        TecnicoLaboratorio tecnico = new TecnicoLaboratorio("João", "98765432100", "joao.tecnico", "senha456");

        assertEquals("Técnico de Laboratório responsável pela análise e status das amostras",
                tecnico.descricaoFuncao());
    }

    @Test
    public void responsavelTecnicoDeveDevolverDescricaoPropria() {
        ResponsavelTecnico responsavel = new ResponsavelTecnico("Ana", "11122233344", "ana.responsavel", "senha789");

        assertEquals("Responsável Técnico responsável pela emissão do laudo",
                responsavel.descricaoFuncao());
    }
}

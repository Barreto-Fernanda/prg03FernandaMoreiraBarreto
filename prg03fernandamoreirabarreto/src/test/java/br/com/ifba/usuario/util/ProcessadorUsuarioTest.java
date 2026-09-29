package br.com.ifba.usuario.util;

// Importa as 3 classes-filhas concretas, que serão usadas para criar os objetos de teste
import br.com.ifba.usuario.entity.Atendente;
import br.com.ifba.usuario.entity.TecnicoLaboratorio;
import br.com.ifba.usuario.entity.ResponsavelTecnico;
// Importa o tipo GERAL, usado para declarar as variáveis (é o que prova o polimorfismo)
import br.com.ifba.usuario.entity.Usuario;
// Importa a anotação @Test e os métodos de asserção (assertEquals, etc.) do JUnit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Testa se ProcessadorUsuario.processar(Usuario) responde corretamente
// para cada tipo concreto de usuário, mesmo recebendo todos pelo tipo geral (Usuario)
public class ProcessadorUsuarioTest {

    @Test
    public void atendenteProcessadoPeloTipoGeralDevolveDescricaoPropria() {
        // Declarado como Usuario (tipo geral), não como Atendente (tipo concreto)
        // isso é o que garante que o teste está checando POLIMORFISMO
        Usuario usuario = new Atendente("Maria", "12345678900", "maria.atendente", "senha123");

        // Confere se processar() devolveu a descrição PRÓPRIA do Atendente,
        assertEquals("Atendente responsável pelo cadastro de amostras e pacientes",
                ProcessadorUsuario.processar(usuario));
    }

    @Test
    public void tecnicoProcessadoPeloTipoGeralDevolveDescricaoPropria() {
        // Mesma ideia, agora com um TecnicoLaboratorio "escondido" atrás do tipo Usuario
        Usuario usuario = new TecnicoLaboratorio("João", "98765432100", "joao.tecnico", "senha456");

        // A saída deve ser DIFERENTE da do Atendente, mesmo chamando o mesmo método processar()
        assertEquals("Técnico de Laboratório responsável pela análise e status das amostras",
                ProcessadorUsuario.processar(usuario));
    }

    @Test
    public void responsavelProcessadoPeloTipoGeralDevolveDescricaoPropria() {
        // Terceira e última variação: prova que o mesmo padrão vale para todas as filhas,
        Usuario usuario = new ResponsavelTecnico("Ana", "11122233344", "ana.responsavel", "senha789");

        assertEquals("Responsável Técnico responsável pela emissão do laudo",
                ProcessadorUsuario.processar(usuario));
    }
}
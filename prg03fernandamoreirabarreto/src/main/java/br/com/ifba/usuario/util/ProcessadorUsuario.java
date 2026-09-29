package br.com.ifba.usuario.util;
import br.com.ifba.usuario.entity.Usuario;

public class ProcessadorUsuario {

    // Recebe o tipo GERAL (Usuario) nunca o tipo concreto (Atendente, TecnicoLaboratorio etç)
    public static String processar(Usuario usuario) {
        return usuario.descricaoFuncao();
    }
}
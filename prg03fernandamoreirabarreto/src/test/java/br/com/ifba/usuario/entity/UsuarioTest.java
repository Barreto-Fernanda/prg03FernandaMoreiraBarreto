package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void autenticarDeveRetornarTrueParaCredenciaisCorretas() {
        Usuario usuario = new Usuario();
        usuario.setLogin("barreto_fernanda");
        usuario.setSenha("Fernanda123");

        assertTrue(usuario.autenticar("barreto_fernanda", "Fernanda123"));
    }

    @Test
    public void autenticarDeveRetornarFalseParaSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setLogin("barreto_fernanda");
        usuario.setSenha("Fernanda123");

        assertFalse(usuario.autenticar("barreto_fernanda", "senhaErrada"));
    }
}
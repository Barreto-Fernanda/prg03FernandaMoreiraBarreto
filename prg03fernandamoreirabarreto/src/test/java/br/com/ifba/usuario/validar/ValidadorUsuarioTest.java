package br.com.ifba.usuario.validar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorUsuarioTest {

    // --- cpfValido ---

    @Test
    public void cpfValidoDeveAceitarCpfComOnzeDigitos() {
        assertTrue(ValidadorUsuario.cpfValido("12345678900"));
    }

    @Test
    public void cpfValidoDeveRejeitarCpfVazio() {
        assertFalse(ValidadorUsuario.cpfValido(""));
    }

    @Test
    public void cpfValidoDeveRejeitarCpfComLetras() {
        assertFalse(ValidadorUsuario.cpfValido("123abc78900"));
    }

    @Test
    public void cpfValidoDeveRejeitarCpfNulo() {
        assertFalse(ValidadorUsuario.cpfValido(null));
    }

    // --- senhaForte ---

    @Test
    public void senhaForteDeveAceitarSenhaComSeisOuMaisCaracteres() {
        assertTrue(ValidadorUsuario.senhaForte("abc123"));
    }

    @Test
    public void senhaForteDeveRejeitarSenhaComMenosCaracteresQueOMinimo() {
        assertFalse(ValidadorUsuario.senhaForte("abc"));
    }

    @Test
    public void senhaForteDeveRejeitarSenhaNula() {
        assertFalse(ValidadorUsuario.senhaForte(null));
    }

    // --- camposPreenchidos ---

    @Test
    public void camposPreenchidosDeveAceitarQuandoTudoPreenchido() {
        assertTrue(ValidadorUsuario.camposPreenchidos(
                "Maria", "12345678900", "01/01/2000", "74999999999",
                "maria@email.com", "maria.santos", "abc123", "abc123"));
    }

    @Test
    public void camposPreenchidosDeveRejeitarQuandoUmCampoEstaVazio() {
        assertFalse(ValidadorUsuario.camposPreenchidos(
                "", "12345678900", "01/01/2000", "74999999999",
                "maria@email.com", "maria.santos", "abc123", "abc123"));
    }

    @Test
    public void camposPreenchidosDeveRejeitarQuandoUmCampoENulo() {
        assertFalse(ValidadorUsuario.camposPreenchidos(
                null, "12345678900", "01/01/2000", "74999999999",
                "maria@email.com", "maria.santos", "abc123", "abc123"));
    }
}
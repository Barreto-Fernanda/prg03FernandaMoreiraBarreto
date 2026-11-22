package br.com.ifba.infrastructure.exception;

// Exceção das REGRAS DE NEGÓCIO: "o sistema não aceita isso" (CPF inválido, senha curta...).
// Diferente da PersistenciaException, que significa "o banco falhou".
// Estende RuntimeException: o Java não obriga try/catch, mas a tela captura para avisar o usuário.
// Não precisa de import, porque RuntimeException já vem com o Java.
public class NegocioException extends RuntimeException {

    // Recebe a mensagem explicativa que a tela vai mostrar ao usuário
    public NegocioException(String mensagem) {
        super(mensagem); // repassa a mensagem para a RuntimeException (é o e.getMessage())
    }
}
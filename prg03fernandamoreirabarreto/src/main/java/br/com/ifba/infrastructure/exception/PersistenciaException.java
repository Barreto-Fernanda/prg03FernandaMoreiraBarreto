package br.com.ifba.infrastructure.exception;

// Exceção de domínio da camada de acesso a dados: o DAO lança esta quando uma
// operação de persistência falha, e a tela só precisa conhecer ela (não o Hibernate).
// Estende RuntimeException, como as outras exceções do projeto.
public class PersistenciaException extends RuntimeException {

    // Recebe a mensagem para o usuário e a causa original (o erro do banco/Hibernate),
    // para não perder a informação de o que realmente deu errado
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
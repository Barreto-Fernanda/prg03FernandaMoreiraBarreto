package br.com.ifba.usuario.exception;

// A NegocioException é a "mãe" de todas as exceções de regra de negócio do projeto
import br.com.ifba.infrastructure.exception.NegocioException;

// Exceção do domínio: representa o problema "este login já está em uso".
// extends NegocioException: continua sendo uma exceção não verificada (o Java não obriga
// try/catch), e agora a tela pode capturar TODAS as regras de negócio de uma vez,
// pegando a NegocioException.
// O nome da classe já diz o que aconteceu, sem precisar ler a mensagem.
public class LoginDuplicadoException extends NegocioException {

    // Guarda o login que causou o problema, caso alguém precise dele depois
    private final String login;

    // O construtor recebe só o login e monta sozinho a mensagem explicativa.
    // Assim, todo lugar que lançar esta exceção mostra o mesmo texto.
    public LoginDuplicadoException(String login) {
        // super(...) passa a mensagem para a NegocioException (e, por ela, para a
        // RuntimeException), e é ela que depois aparece em e.getMessage()
        super("O login \"" + login + "\" já está em uso. Escolha outro login.");
        this.login = login;
    }

    // Permite consultar qual foi o login repetido
    public String getLogin() {
        return login;
    }
}
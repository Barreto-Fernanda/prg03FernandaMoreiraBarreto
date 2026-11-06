package br.com.ifba.usuario.exception;


// Exceção do domínio: representa o problema "este login já está em uso".
// Estende RuntimeException, então é uma exceção "não verificada": o Java não obriga
// quem chama o método a usar try/catch, mas a tela pode capturar para avisar o usuário.
// O nome da classe já diz o que aconteceu, sem precisar ler a mensagem.
public class LoginDuplicadoException extends RuntimeException {

    // Guarda o login que causou o problema, caso alguém precise dele depois
    private final String login;

    // O construtor recebe só o login e monta sozinho a mensagem explicativa.
    // Assim, todo lugar que lançar esta exceção mostra o mesmo texto.
    public LoginDuplicadoException(String login) {
        // super(...) passa a mensagem para a RuntimeException,
        // e é ela que depois aparece em e.getMessage()
        super("O login \"" + login + "\" já está em uso. Escolha outro login.");
        this.login = login;
    }

    // Permite consultar qual foi o login repetido
    public String getLogin() {
        return login;
    }
}

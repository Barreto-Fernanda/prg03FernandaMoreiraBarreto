package br.com.ifba.usuario.exception;

// Exceção do domínio: representa o problema "não existe usuário com este login".
// Estende RuntimeException, como a LoginDuplicadoException: o Java não obriga a usar
// try/catch, mas quem chama o método pode capturar para avisar o usuário.
public class UsuarioNaoEncontradoException extends RuntimeException {

    // Guarda o login que foi procurado e não existe, caso alguém precise dele depois
    private final String login;

    // O construtor recebe só o login e monta sozinho a mensagem explicativa.
    // Assim, todo lugar que lançar esta exceção mostra o mesmo texto.
    public UsuarioNaoEncontradoException(String login) {
        // super(...) passa a mensagem para a RuntimeException,
        // e é ela que depois aparece em e.getMessage()
        super("Nenhum usuário encontrado com o login \"" + login + "\".");
        this.login = login;
    }

    // Permite consultar qual login foi procurado
    public String getLogin() {
        return login;
    }
}
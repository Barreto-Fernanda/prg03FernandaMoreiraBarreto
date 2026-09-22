package br.com.ifba.usuario.validar;

// Centraliza as regras de validação do cadastro de usuário.
// A tela apenas chama esses métodos e exibe a mensagem nenhuma regra fica no listener.
public class ValidadorUsuario {

    // Verifica se todos os campos obrigatórios foram preenchidos
    public static boolean camposPreenchidos(String nome, String cpf, String dataNascimento,
            String telefone, String email, String login, String senha, String confirmarSenha) {

        return nome != null && !nome.isEmpty()
                && cpf != null && !cpf.isEmpty()
                && dataNascimento != null && !dataNascimento.isEmpty()
                && telefone != null && !telefone.isEmpty()
                && email != null && !email.isEmpty()
                && login != null && !login.isEmpty()
                && senha != null && !senha.isEmpty()
                && confirmarSenha != null && !confirmarSenha.isEmpty();
    }

    // Verifica se a senha e a confirmação são iguais
    public static boolean senhasCoincidem(String senha, String confirmarSenha) {
        if (senha == null || confirmarSenha == null) {
            return false;
        }
        return senha.equals(confirmarSenha);
    }

    // Verifica se o CPF tem um formato válido: só dígitos, 11 caracteres
    public static boolean cpfValido(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }
        return cpf.matches("\\d{11}");
    }

    // Verifica se a senha tem tamanho mínimo (senha "forte" simples: 6+ caracteres)
    public static boolean senhaForte(String senha) {
        if (senha == null) {
            return false;
        }
        return senha.length() >= 6;
    }

    // Verifica se o texto (aqui, o login) contém alguma palavra proibida
    public static boolean contemPalavraProibida(String texto) {
        String[] palavrasProibidas = {"admin", "teste", "root", "senha123"};

        for (String palavra : palavrasProibidas) {
            if (texto.toLowerCase().contains(palavra)) {
                return true;
            }
        }

        return false;
    }
}
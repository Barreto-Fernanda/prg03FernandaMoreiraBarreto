package br.com.ifba.usuario.validar;

public class ValidadorUsuario {

    public static boolean contemPalavraProibida(String texto) {

        // Vetor local de palavras proibidas
        String[] palavrasProibidas = {"admin", "teste", "root", "senha123"};

        // Percorre cada palavra do vetor e verifica se o texto a contém
        for (String palavra : palavrasProibidas) {
            if (texto.toLowerCase().contains(palavra)) {
                return true; // achou uma palavra proibida, para aqui e retorna true
            }
        }

        return false; // percorreu tudo e não achou nenhuma
    }
}

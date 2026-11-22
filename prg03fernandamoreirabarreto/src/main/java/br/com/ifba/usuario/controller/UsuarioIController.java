package br.com.ifba.usuario.controller;

// Entidade que o Controller recebe da tela e devolve a ela
import br.com.ifba.usuario.entity.Usuario;
// Tipo da lista que findAll e findByNome devolvem
import java.util.List;

// Contrato do Controller de Usuario: é a única coisa que a View conhece.
// A tela sabe que existe um UsuarioIController, mas não sabe que existe UsuarioController.
// Os nomes seguem o mesmo vocabulário das outras camadas (save, update, delete, find...)
public interface UsuarioIController {

    // Grava um usuário novo
    Usuario save(Usuario usuario);

    // Atualiza um usuário existente
    Usuario update(Usuario usuario);

    // Remove o usuário
    void delete(Usuario usuario);

    // Busca pelo id; devolve null se não existir
    Usuario findById(Long id);

    // Devolve todos os usuários gravados
    List<Usuario> findAll();

    // Busca pelo login; devolve null se não existir
    Usuario findByLogin(String login);

    // Busca os usuários cujo nome contém o texto, sem diferenciar maiúsculas de minúsculas
    List<Usuario> findByNome(String texto);
}
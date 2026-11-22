package br.com.ifba.usuario.service;

// Entidade com a qual este Service trabalha
import br.com.ifba.usuario.entity.Usuario;
// Tipo da lista que findAll e findByNome devolvem
import java.util.List;

// Contrato do Service de Usuario: é a única coisa que o Controller conhece desta camada.
// A interface diz O QUE o Service faz; a classe UsuarioService diz COMO.
// Os nomes seguem o vocabulário padrão do projeto (save, update, delete, find...).
public interface UsuarioIService {

    // Grava um usuário novo, depois de aplicar as regras de negócio
    Usuario save(Usuario usuario);

    // Atualiza um usuário existente, depois de aplicar as regras de negócio
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
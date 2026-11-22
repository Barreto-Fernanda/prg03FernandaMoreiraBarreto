package br.com.ifba.usuario.controller;

// Entidade que passa pelo Controller sem ser alterada
import br.com.ifba.usuario.entity.Usuario;
// Contrato do Service: o Controller só conhece a interface, nunca a classe UsuarioService
import br.com.ifba.usuario.service.UsuarioIService;
// Tipo da lista que findAll e findByNome devolvem
import java.util.List;

// Controller de Usuario: recebe da tela e encaminha ao Service. É um mensageiro:
// não valida nem altera nada, cada método tem uma linha.
// Num app desktop ele só repassa; existe para espelhar a arquitetura web (Spring)
public class UsuarioController implements UsuarioIController {

    // O Service com quem o Controller conversa (tipo da INTERFACE)
    private final UsuarioIService service;

    // Injeção de dependência: o Controller RECEBE o Service pelo construtor, não cria com new
    public UsuarioController(UsuarioIService service) {
        this.service = service;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return service.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return service.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        service.delete(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return service.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return service.findAll();
    }

    @Override
    public Usuario findByLogin(String login) {
        return service.findByLogin(login);
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        return service.findByNome(texto);
    }
}
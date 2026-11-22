package br.com.ifba.usuario.service;

// Exceção de regra de negócio genérica (CPF inválido, senha curta, palavra proibida)
import br.com.ifba.infrastructure.exception.NegocioException;
// Contrato do DAO: o Service só conhece a interface, nunca a classe UsuarioDao
import br.com.ifba.usuario.dao.UsuarioIDao;
// Entidade com a qual este Service trabalha
import br.com.ifba.usuario.entity.Usuario;
// Exceção lançada quando o login já pertence a outro usuário
import br.com.ifba.usuario.exception.LoginDuplicadoException;
// Validações já prontas (CPF, tamanho da senha, palavra proibida), reaproveitadas aqui
import br.com.ifba.usuario.validar.ValidadorUsuario;
// Tipo da lista que findAll e findByNome devolvem
import java.util.List;

// Service de Usuario: é o "juiz do domínio". Decide se a operação pode acontecer
// e só então entrega o usuário ao DAO. Se alguma regra falhar, ninguém chega ao banco.
public class UsuarioService implements UsuarioIService {

    // O DAO com quem o Service conversa. É o tipo da INTERFACE: o Service não sabe
    // se por trás está o MySQL ou um FakeDao de teste
    private final UsuarioIDao dao;

    // Injeção de dependência: o Service RECEBE o DAO pelo construtor, não cria com new.
    // Quem monta tudo (o main) decide qual implementação entregar
    public UsuarioService(UsuarioIDao dao) {
        this.dao = dao;
    }

    // Grava um usuário novo: primeiro julga, depois persiste
    @Override
    public Usuario save(Usuario usuario) {
        validarRegras(usuario);
        return dao.save(usuario);
    }

    // Atualiza um usuário existente: as mesmas regras valem na edição
    @Override
    public Usuario update(Usuario usuario) {
        validarRegras(usuario);
        return dao.update(usuario);
    }

    // Os métodos abaixo não têm regra de negócio: só repassam ao DAO
    @Override
    public void delete(Usuario usuario) {
        dao.delete(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return dao.findAll();
    }

    @Override
    public Usuario findByLogin(String login) {
        return dao.findByLogin(login);
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        return dao.findByNome(texto);
    }

    // Reúne todas as regras de negócio do usuário. Cada regra que falha LANÇA
    // uma exceção, e o código para ali: nada chega ao DAO.
    // Valem para qualquer tela (ou API) que use este Service
    private void validarRegras(Usuario usuario) {
        // Regra 1: CPF precisa ser válido (11 números)
        if (!ValidadorUsuario.cpfValido(usuario.getCpf())) {
            throw new NegocioException("CPF inválido. Use apenas 11 números.");
        }

        // Regra 2: senha com no mínimo 6 caracteres
        if (!ValidadorUsuario.senhaForte(usuario.getSenha())) {
            throw new NegocioException("Senha muito curta. Use ao menos 6 caracteres.");
        }

        // Regra 3: o login não pode conter palavra proibida
        if (ValidadorUsuario.contemPalavraProibida(usuario.getLogin())) {
            throw new NegocioException("Login contém palavra não permitida.");
        }

        // Regra 4: o login não pode pertencer a OUTRO usuário.
        // Busca quem já usa este login (null se ninguém usa)
        Usuario existente = dao.findByLogin(usuario.getLogin());

        // "Repetido" só se achou alguém E esse alguém não é o próprio usuário.
        // Usuário novo tem id null, então qualquer existente conta como repetido.
        // Na edição, o id do existente é igual ao do usuário, então não é repetido
        if (existente != null && !existente.getId().equals(usuario.getId())) {
            throw new LoginDuplicadoException(usuario.getLogin());
        }
    }
}
# Auditoria SOLID do projeto

Projeto: `prg03fernandamoreirabarreto` (caminho base: `src/main/java/br/com/ifba/`).
Números de linha conferidos no NetBeans (Show Line Numbers ligado).

## Task 1: um exemplo concreto de cada princípio

### S: Responsabilidade Única

| Arquivo | Linha | Trecho |
|---|---|---|
| `usuario/service/UsuarioService.java` | 73 | `private void validarRegras(Usuario usuario)` |
| `usuario/dao/UsuarioDao.java` | 18 | `public class UsuarioDao extends GenericDao<Usuario>` |

**Por quê:** o `UsuarioService` só muda quando a regra de negócio muda (CPF, senha mínima, palavra proibida, login repetido, linhas 74 a 98). O `UsuarioDao` só muda quando a forma de consultar o banco muda (`findByLogin` e `findByNome`). Ele não julga nada: o comentário das linhas 15 a 17 registra que a regra do login único saiu do DAO e foi para o Service.

### O: Aberto/Fechado

| Arquivo | Linha | Trecho |
|---|---|---|
| `infrastructure/dao/GenericDao.java` | 17 | `public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity>` |
| `usuario/dao/UsuarioDao.java` | 18 a 23 | `extends GenericDao<Usuario>` e construtor `super(Usuario.class)` |

**Por quê:** o `GenericDao` foi escrito uma vez e não foi editado para o sistema ganhar a entidade `Usuario`. O `UsuarioDao` acrescenta comportamento (as buscas próprias) estendendo a classe, sem alterar uma linha do `save`, `update`, `delete`, `findById` e `findAll`. Uma entidade nova ganharia um DAO novo do mesmo jeito.

### L: Substituição de Liskov

| Arquivo | Linha | Trecho |
|---|---|---|
| `usuario/dao/UsuarioDao.java` | 18 | `UsuarioDao extends GenericDao<Usuario>` |
| `infrastructure/entity/PersistenceEntity.java` | 18 | `public abstract class PersistenceEntity` |
| `usuario/entity/Usuario.java` | 21 | `public class Usuario extends PersistenceEntity` |

**Por quê:** onde se espera um `GenericDao<Usuario>`, o `UsuarioDao` entra sem surpresa: faz tudo o que a classe-mãe faz e mais. O mesmo vale para `Usuario` no lugar de `PersistenceEntity`: todo `Usuario` é uma entidade persistente (teste da frase "todo X é um Y"). Já a herança de `Pessoa` foi evitada, porque `Usuario` não é uma `Pessoa`.

### I: Segregação de Interfaces

| Arquivo | Linha | Trecho |
|---|---|---|
| `infrastructure/dao/GenericIDao.java` | 18 a 30 | as cinco assinaturas: `save`, `update`, `delete`, `findById`, `findAll` |
| `usuario/dao/UsuarioIDao.java` | 16 e 19 | `findByLogin(String login)` e `findByNome(String texto)` |

**Por quê:** o contrato geral tem só o que toda entidade sabe fazer. As buscas por login e por nome ficam no `UsuarioIDao`, porque só o `Usuario` tem login. Se estivessem no `GenericIDao`, qualquer outro DAO seria obrigado a implementar um método sem sentido para ele.

### D: Inversão de Dependência

| Arquivo | Linha | Trecho |
|---|---|---|
| `usuario/service/UsuarioService.java` | 22 e 26 a 28 | `private final UsuarioIDao dao;` e construtor `UsuarioService(UsuarioIDao dao)` |
| `com/mycompany/prg03fernandamoreirabarreto/Prg03fernandamoreirabarreto.java` | 23 a 25 | `new UsuarioDao()`, `new UsuarioService(dao)`, `new UsuarioController(service)` |

**Por quê:** o `UsuarioService` depende da abstração (`UsuarioIDao`) e recebe a dependência pelo construtor, sem criá-la com `new`. Quem escolhe a implementação é o `main`, o único lugar que dá `new` nas classes concretas. Por isso o mesmo Service roda em produção com `UsuarioDao` e nos testes com `FakeUsuarioDao`, sem mudar uma linha.

## Task 2: violações encontradas

### Violação 1: S (Responsabilidade Única) — CORRIGIDA (ver Task 3)

**Arquivo:** `usuario/view/TelaCadastroUsuario.java`, método `btnCadastrarActionPerformed`, linhas 229 a 312 (de antes da correção).

**Por quê:** um único método de cerca de 80 linhas faz cinco trabalhos: lê os campos (231 a 238), valida por conveniência (243 a 251), decide entre cadastro e edição e monta o objeto (256 a 271), chama o Controller (273 a 278) e traduz três tipos de exceção em mensagens (288 a 308). Ele tem vários motivos para mudar: layout, regra de conveniência, texto de erro e fluxo de edição. Os comentários que separam as partes mostram que cada parte já é um método (regra prática da aula).

### Violação 2: S (Responsabilidade Única) — NÃO corrigida

**Arquivo:** `login/view/TelaLogin.java`, método `btnEntrarActionPerformed`, linhas 125 a 131.

**Por quê:** a tela decide quem pode entrar. Cria um `Usuario` com login e senha fixos no código (`barreto_fernanda` e `Fernanda123`) e chama `autenticar`. Pelo teste da aula ("se amanhã trocasse a tela Swing por uma API web, essa regra continuaria valendo?"), autenticar é regra de negócio e pertence ao Service, não à view. Além disso, a credencial fica escrita no código-fonte.

### Violação 3: S (Responsabilidade Única) e D (Inversão de Dependência) — CORRIGIDA (ver Task 3)

**Arquivo:** `usuario/repository/RepositorioUsuarioJPA.java`, classe inteira (linhas 10 a 140, de antes de ser removida).

**Por quê:**
- Mistura acesso a dados com regra de negócio: o método `garantirLoginDisponivel` (linha 116) decide se o login é repetido, regra que hoje mora no `UsuarioService` (linhas 89 a 98). A mesma regra existe em dois lugares.
- Repete o bloco `try/catch/finally` com `EntityManager` em cinco métodos (linhas 14, 37, 51, 73 e 95), que o `GenericDao` já centraliza (DRY).
- É uma classe concreta sem interface, fora da arquitetura em camadas. Hoje nenhuma tela a usa; só o `RepositorioUsuarioJPATest`.

### Violação 4: D (Inversão de Dependência), leve — NÃO corrigida

**Arquivo:** `usuario/service/UsuarioService.java`, método `validarRegras`, linhas 75, 80 e 85.

**Por quê:** o Service chama métodos estáticos da classe concreta `ValidadorUsuario` (`cpfValido`, `senhaForte`, `contemPalavraProibida`). Essa dependência não entra pelo construtor, então não dá para trocá-la nos testes. É uma violação leve, porque o validador não acessa banco nem tela e é fácil de testar, mas foge do padrão aplicado ao DAO.

## Task 3: duas violações corrigidas

### Correção da Violação 1 (princípio S)

- **O que mudou:** o `btnCadastrarActionPerformed`, que fazia cinco trabalhos em cerca de 80 linhas, foi quebrado em métodos pequenos na própria `TelaCadastroUsuario`, cada um com uma única responsabilidade (por exemplo, `montarUsuario` cria o objeto `Usuario` a partir dos campos da tela, e `preencherCampos` faz o caminho inverso na edição).
- **Princípio atendido:** S (Responsabilidade Única): cada trecho passou a ter um único motivo para mudar.
- **Comportamento:** não mudou. Conferido na mão (cadastro válido, login repetido e edição) e pela suíte de testes.

### Correção da Violação 3 (princípios S e D, e DRY)

- **O que mudou:** a classe `RepositorioUsuarioJPA` e o teste dela (`RepositorioUsuarioJPATest`) foram removidos. Antes de apagar, a busca da IDE confirmou que só esses dois arquivos a usavam. O acesso ao banco fica no `GenericDao` e no `UsuarioDao`, atrás da interface `UsuarioIDao`, e a regra do login repetido fica só no `UsuarioService`.
- **Princípios atendidos:** S (acesso a dados e regra de negócio deixam de se misturar), D (o acesso ao banco passa só pelas interfaces da arquitetura em camadas) e DRY (o `try/catch/finally` com `EntityManager` existe em um único lugar).
- **Testes:** a suíte passou de 44 para 40 testes, porque os 4 do repositório removido saíram junto com a classe. A regra do login repetido continua coberta pelo `UsuarioServiceTest`. Todos os 40 passam.

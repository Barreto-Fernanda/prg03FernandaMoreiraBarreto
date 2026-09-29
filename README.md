# \## Estrutura do Projeto

# 

# ```

# &#x20;C:.

# │   .gitattributes

# │   .gitignore

# │   desktop.ini

# │   LICENSE

# │   README.md

# │

# ├───ATIVIDADES

# │   ├───ATIVIDADE 01 - DOCUMENTO REQUISITOS

# │   │       documento-de-requisitos.pdf

# │   │

# │   ├───ATIVIDADE 02 - LEARNGIT

# │   │       print - learngit.png

# │   │

# │   ├───ATIVIDADE 03 - LEARNGIT

# │   │       ATIVIDADE 03 - LEARNGIT.png

# │   │

# │   ├───ATIVIDADE 04 - PROJETO E TELA LOGIN

# │   │       Tela Login.png

# │   │

# │   ├───ATIVIDADE 05 - TELA CADASTRO

# │   │       tela de cadastro com o JOptionPane.png

# │   │

# │   ├───ATIVIDADE 06 - VALIDAÇÃO CADASTRO

# │   │       Palavra não permitida.png

# │   │

# │   ├───ATIVIDADE 07 - CLASSE USUARIO

# │   │       Tela\_Cadastro\_Funcionando.png

# │   │       Tela\_Login\_Funcionando.png

# │   │

# │   ├───ATIVIDADE 08 - DIAGRAMA USUARIO

# │   │       classe usuario refatorada.png

# │   │       Diagrama de Usuario.drawio.png

# │   │

# │   ├───ATIVIDADE 09 - INTERFACE

# │   │       classe Usuario refatorada - autenticar.png

# │   │       classe Usuario refatorada - implements.png

# │   │       Diagrama da interface.jpg

# │   │

# │   ├───ATIVIDADE 10 - TESTES

# │   │       Testes.png

# │   │

# │   ├───ATIVIDADE 11 - RELACIONAMENTO

# │   ├───ATIVIDADE 12 - HERANÇA

# │   │       Test results.png

# │   │

# │   └───ATIVIDADE 13 - POLIMORFISMO

# │           All 22 tests passed.png

# │

# └───prg03fernandamoreirabarreto

# &#x20;   │   pom.xml

# &#x20;   │

# &#x20;   ├───nbproject

# &#x20;   │       project.properties

# &#x20;   │

# &#x20;   ├───src

# &#x20;   │   ├───main

# &#x20;   │   │   ├───java

# &#x20;   │   │   │   ├───br

# &#x20;   │   │   │   │   └───com

# &#x20;   │   │   │   │       └───ifba

# &#x20;   │   │   │   │           ├───amostra

# &#x20;   │   │   │   │           │   ├───entity

# &#x20;   │   │   │   │           │   │       Amostra.java

# &#x20;   │   │   │   │           │   │       Exame.java

# &#x20;   │   │   │   │           │   │       Paciente.java

# &#x20;   │   │   │   │           │   │

# &#x20;   │   │   │   │           │   └───enums

# &#x20;   │   │   │   │           │           StatusAmostra.java

# &#x20;   │   │   │   │           │

# &#x20;   │   │   │   │           ├───login

# &#x20;   │   │   │   │           │   └───view

# &#x20;   │   │   │   │           │           TelaLogin.form

# &#x20;   │   │   │   │           │           TelaLogin.java

# &#x20;   │   │   │   │           │

# &#x20;   │   │   │   │           └───usuario

# &#x20;   │   │   │   │               ├───entity

# &#x20;   │   │   │   │               │       Atendente.java

# &#x20;   │   │   │   │               │       ResponsavelTecnico.java

# &#x20;   │   │   │   │               │       TecnicoLaboratorio.java

# &#x20;   │   │   │   │               │       Usuario.java

# &#x20;   │   │   │   │               │

# &#x20;   │   │   │   │               ├───imagens

# &#x20;   │   │   │   │               │       Logo.png

# &#x20;   │   │   │   │               │

# &#x20;   │   │   │   │               ├───interfaces

# &#x20;   │   │   │   │               │       Autenticavel.java

# &#x20;   │   │   │   │               │

# &#x20;   │   │   │   │               ├───util

# &#x20;   │   │   │   │               │       ProcessadorUsuario.java

# &#x20;   │   │   │   │               │

# &#x20;   │   │   │   │               ├───validar

# &#x20;   │   │   │   │               │       ValidadorUsuario.java

# &#x20;   │   │   │   │               │

# &#x20;   │   │   │   │               └───view

# &#x20;   │   │   │   │                       TelaCadastroUsuario.form

# &#x20;   │   │   │   │                       TelaCadastroUsuario.java

# &#x20;   │   │   │   │

# &#x20;   │   │   │   └───com

# &#x20;   │   │   │       └───mycompany

# &#x20;   │   │   │           └───prg03fernandamoreirabarreto

# &#x20;   │   │   │                   Prg03fernandamoreirabarreto.java

# &#x20;   │   │   │

# &#x20;   │   │   └───resources

# &#x20;   │   │       └───br

# &#x20;   │   │           └───com

# &#x20;   │   │               └───ifba

# &#x20;   │   │                   └───login

# &#x20;   │   │                       └───imagens

# &#x20;   │   │                               Logo.png

# &#x20;   │   │

# &#x20;   │   └───test

# &#x20;   │       └───java

# &#x20;   │           └───br

# &#x20;   │               └───com

# &#x20;   │                   └───ifba

# &#x20;   │                       ├───amostra

# &#x20;   │                       │   └───entity

# &#x20;   │                       │           AmostraTest.java

# &#x20;   │                       │

# &#x20;   │                       └───usuario

# &#x20;   │                           ├───entity

# &#x20;   │                           │       UsuarioHierarquiaTest.java

# &#x20;   │                           │       UsuarioTest.java

# &#x20;   │                           │

# &#x20;   │                           ├───util

# &#x20;   │                           │       ProcessadorUsuarioTest.java

# &#x20;   │                           │

# &#x20;   │                           └───validar

# &#x20;   │                                   ValidadorUsuarioTest.java

# &#x20;   │

# &#x20;   └───target

# &#x20;       ├───classes

# &#x20;       │   ├───br

# &#x20;       │   │   └───com

# &#x20;       │   │       └───ifba

# &#x20;       │   │           ├───amostra

# &#x20;       │   │           │   ├───entity

# &#x20;       │   │           │   │       Amostra.class

# &#x20;       │   │           │   │       Exame.class

# &#x20;       │   │           │   │       Paciente.class

# &#x20;       │   │           │   │

# &#x20;       │   │           │   └───enums

# &#x20;       │   │           │           StatusAmostra.class

# &#x20;       │   │           │

# &#x20;       │   │           ├───login

# &#x20;       │   │           │   ├───imagens

# &#x20;       │   │           │   │       Logo.png

# &#x20;       │   │           │   │

# &#x20;       │   │           │   └───view

# &#x20;       │   │           │           TelaLogin.class

# &#x20;       │   │           │

# &#x20;       │   │           └───usuario

# &#x20;       │   │               ├───entity

# &#x20;       │   │               │       Atendente.class

# &#x20;       │   │               │       ResponsavelTecnico.class

# &#x20;       │   │               │       TecnicoLaboratorio.class

# &#x20;       │   │               │       Usuario.class

# &#x20;       │   │               │

# &#x20;       │   │               ├───interfaces

# &#x20;       │   │               │       Autenticavel.class

# &#x20;       │   │               │

# &#x20;       │   │               ├───util

# &#x20;       │   │               │       ProcessadorUsuario.class

# &#x20;       │   │               │

# &#x20;       │   │               ├───validar

# &#x20;       │   │               │       ValidadorUsuario.class

# &#x20;       │   │               │

# &#x20;       │   │               └───view

# &#x20;       │   │                       TelaCadastroUsuario.class

# &#x20;       │   │

# &#x20;       │   └───com

# &#x20;       │       └───mycompany

# &#x20;       │           └───prg03fernandamoreirabarreto

# &#x20;       │                   Prg03fernandamoreirabarreto.class

# &#x20;       │

# &#x20;       ├───generated-sources

# &#x20;       │   └───annotations

# &#x20;       ├───generated-test-sources

# &#x20;       │   └───test-annotations

# &#x20;       ├───maven-status

# &#x20;       │   └───maven-compiler-plugin

# &#x20;       │       ├───compile

# &#x20;       │       │   └───default-compile

# &#x20;       │       │           createdFiles.lst

# &#x20;       │       │           inputFiles.lst

# &#x20;       │       │

# &#x20;       │       └───testCompile

# &#x20;       │           └───default-testCompile

# &#x20;       │                   createdFiles.lst

# &#x20;       │                   inputFiles.lst

# &#x20;       │

# &#x20;       ├───surefire-reports

# &#x20;       │       br.com.ifba.amostra.entity.AmostraTest.txt

# &#x20;       │       br.com.ifba.usuario.entity.UsuarioHierarquiaTest.txt

# &#x20;       │       br.com.ifba.usuario.entity.UsuarioTest.txt

# &#x20;       │       br.com.ifba.usuario.util.ProcessadorUsuarioTest.txt

# &#x20;       │       br.com.ifba.usuario.validar.ValidadorUsuarioTest.txt

# &#x20;       │       TEST-br.com.ifba.amostra.entity.AmostraTest.xml

# &#x20;       │       TEST-br.com.ifba.usuario.entity.UsuarioHierarquiaTest.xml

# &#x20;       │       TEST-br.com.ifba.usuario.entity.UsuarioTest.xml

# &#x20;       │       TEST-br.com.ifba.usuario.util.ProcessadorUsuarioTest.xml

# &#x20;       │       TEST-br.com.ifba.usuario.validar.ValidadorUsuarioTest.xml

# &#x20;       │

# &#x20;       └───test-classes

# &#x20;           └───br

# &#x20;               └───com

# &#x20;                   └───ifba

# &#x20;                       ├───amostra

# &#x20;                       │   └───entity

# &#x20;                       │           AmostraTest.class

# &#x20;                       │

# &#x20;                       └───usuario

# &#x20;                           ├───entity

# &#x20;                           │       UsuarioHierarquiaTest.class

# &#x20;                           │       UsuarioTest.class

# &#x20;                           │

# &#x20;                           ├───util

# &#x20;                           │       ProcessadorUsuarioTest.class

# &#x20;                           │

# &#x20;                           └───validar

# &#x20;                                   ValidadorUsuarioTest.class

# ```



\## Sobrecarga: construtores de Usuario

\- `Usuario()`: cria um usuário "em branco", útil quando os dados vão ser preenchidos aos poucos

&#x20; (ex.: um formulário de cadastro passo a passo, ou testes que só precisam de alguns atributos).

\- `Usuario(nome, cpf, login, senha)`: cria o usuário já com os dados principais, útil quando

&#x20; todos esses valores já estão disponíveis de uma vez (ex.: dados capturados da Tela de Cadastro).


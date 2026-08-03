# BiblioTech

Sistema de gerenciamento de biblioteca escolar/acadêmica.

## Status do projeto

🚧 **Em desenvolvimento**

## Tecnologias

- Java 17
- Java Swing (interface gráfica)
- MySQL 8
- JDBC
- Maven
- NetBeans IDE

## Time de desenvolvedores

- Patrick Dietrich

## Objetivo do software

O BiblioTech tem como objetivo facilitar o controle do acervo e das operações de uma biblioteca, permitindo o cadastro de livros e usuários, o registro de empréstimos e devoluções, além do acompanhamento da disponibilidade de exemplares e da situação dos empréstimos (ativos ou atrasados).

## Funcionalidades do sistema (requisitos)

| Código | Funcionalidade                          | Descrição |
|--------|-----------------------------------------|-----------|
| RF01   | Cadastro de livros                      | Permite incluir, listar e excluir livros do acervo (título, autor, categoria e quantidade de exemplares). |
| RF02   | Cadastro de usuários                    | Permite cadastrar alunos e professores (nome, tipo, matrícula e contato). |
| RF03   | Registro de empréstimo                  | Registra o empréstimo de um livro disponível a um usuário, definindo data de retirada e data prevista de devolução (padrão: +7 dias). |
| RF04   | Registro de devolução                   | Registra a devolução de um empréstimo ativo, atualizando o status e a quantidade disponível do livro. |
| RF05   | Autenticação do bibliotecário           | Exige login e senha para acesso ao sistema (senha armazenada com hash MD5). |
| RF06   | Consulta de empréstimos abertos         | Lista os empréstimos com status ATIVO. |
| RF07   | Consulta de empréstimos atrasados       | Identifica empréstimos ativos cuja data prevista de devolução já venceu. |
| RF08   | Controle de disponibilidade             | Atualiza automaticamente a quantidade de exemplares disponíveis a cada empréstimo e devolução. |

## Como executar

1. Crie o banco de dados executando o script SQL no MySQL Workbench.
2. Ajuste usuário e senha em `ConexaoBD.java` se necessário.
3. Abra o projeto no NetBeans e execute a classe `BiblioTech`.

**Credenciais padrão de acesso**
- Usuário: `admin`
- Senha: `123`

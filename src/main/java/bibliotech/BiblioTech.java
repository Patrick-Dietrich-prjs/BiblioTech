package bibliotech;

import bibliotech.view.loginView;

/**
 * Classe principal do projeto.
 *
 * Nesta etapa, apenas a funcionalidade de CADASTRO DE LIVRO (RF01) está
 * prototipada de ponta a ponta: leitura dos dados via console, gravação
 * no banco através do LivroDAO e exibição da lista de livros disponíveis
 * logo em seguida. As demais funcionalidades (usuários, empréstimos e
 * devoluções) serão implementadas nas próximas etapas, quando as telas
 * gráficas (Swing) forem construídas.
 *
 * Pré-requisito para executar: banco MySQL configurado (ver scripts SQL
 * nos comentários das classes DAO) e driver MySQL Connector/J adicionado
 * às bibliotecas do projeto no NetBeans.
 */

public class BiblioTech {

    public static void main(String[] args) {
        new loginView().setVisible(true);
    }
}

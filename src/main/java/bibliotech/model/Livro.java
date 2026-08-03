package bibliotech.model;

/**
 * Representa um livro do acervo da biblioteca.
 */
public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int qtdExemplares;
    private int qtdDisponivel;

    public Livro() {
    }

    public Livro(String titulo, String autor, String categoria, int qtdExemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.qtdExemplares = qtdExemplares;
        this.qtdDisponivel = qtdExemplares;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQtdExemplares() {
        return qtdExemplares;
    }

    public void setQtdExemplares(int qtdExemplares) {
        this.qtdExemplares = qtdExemplares;
    }

    public int getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(int qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    /**
     * Indica se há ao menos um exemplar disponível para empréstimo.
     * @return 
     */
    public boolean temExemplarDisponivel() {
        return qtdDisponivel > 0;
    }

    @Override
    public String toString() {
        return "Livro{" + "id=" + id + ", titulo=" + titulo + ", autor=" + autor
                + ", categoria=" + categoria + ", qtdExemplares=" + qtdExemplares
                + ", qtdDisponivel=" + qtdDisponivel + '}';
    }
}
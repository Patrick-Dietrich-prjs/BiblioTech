package bibliotech.model;

import java.time.LocalDate;

/**
 * Representa o empréstimo de um livro a um usuário.
 */
public class Emprestimo {

    private int id;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataRetirada;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataEfetivaDevolucao;
    private StatusEmprestimo status;

    public Emprestimo() {
    }

    public Emprestimo(Livro livro, Usuario usuario, LocalDate dataRetirada, LocalDate dataPrevistaDevolucao) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataRetirada = dataRetirada;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.status = StatusEmprestimo.ATIVO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDate getDataEfetivaDevolucao() {
        return dataEfetivaDevolucao;
    }

    public void setDataEfetivaDevolucao(LocalDate dataEfetivaDevolucao) {
        this.dataEfetivaDevolucao = dataEfetivaDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    /**
     * Verifica se o empréstimo está atrasado (ainda ativo e com data prevista já vencida).
     * @return 
     */
    public boolean estaAtrasado() {
        return status == StatusEmprestimo.ATIVO
                && dataPrevistaDevolucao != null
                && LocalDate.now().isAfter(dataPrevistaDevolucao);
    }

    @Override
    public String toString() {
        return "Emprestimo{" + "id=" + id
                + ", livro=" + (livro != null ? livro.getTitulo() : null)
                + ", usuario=" + (usuario != null ? usuario.getNome() : null)
                + ", dataRetirada=" + dataRetirada
                + ", dataPrevistaDevolucao=" + dataPrevistaDevolucao
                + ", dataEfetivaDevolucao=" + dataEfetivaDevolucao
                + ", status=" + status + '}';
    }
}
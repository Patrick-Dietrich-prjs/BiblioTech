package bibliotech.model;

/**
 * Representa um usuário da biblioteca (aluno ou professor).
 */
public class Usuario {

    private int id;
    private String nome;
    private TipoUsuario tipo;
    private String matricula;
    private String contato;

    public Usuario() {
    }

    public Usuario(String nome, TipoUsuario tipo, String matricula, String contato) {
        this.nome = nome;
        this.tipo = tipo;
        this.matricula = matricula;
        this.contato = contato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + ", tipo=" + tipo
                + ", matricula=" + matricula + ", contato=" + contato + '}';
    }
}
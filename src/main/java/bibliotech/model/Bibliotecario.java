package bibliotech.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Representa o usuário responsável por operar o sistema (login obrigatório).
 */
public class Bibliotecario {

    private int id;
    private String login;
    private String senha; // armazenada como hash MD5

    public Bibliotecario() {
    }

    public Bibliotecario(String login, String senha) {
        this.login = login;
        this.senha = criptografar(senha);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Verifica se a senha informada corresponde à senha cadastrada (comparando hashes).
     * @return 
     */
    public boolean autenticar(String senhaDigitada) {
        return this.senha != null && this.senha.equals(criptografar(senhaDigitada));
    }

    private static String criptografar(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha.", e);
        }
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

/**
 *
 * @author marcus
 */
public class Cliente extends Pessoa{
    private String genero;

    public Cliente(String nome, String email, User usuario, String genero) {
        super(nome, email, usuario);
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
 
}

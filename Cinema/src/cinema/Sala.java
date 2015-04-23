/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

/**
 *
 * @author henrique
 */
public class Sala {
    private int numero;
    private int capacidade;
    private String tipo;
    private User usuario;

    public Sala(int numero, int capacidade, String tipo, User usuario) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return numero+"";
    }
 
}

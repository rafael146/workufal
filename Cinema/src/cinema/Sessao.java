/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

/**
 *
 * @author henrique
 */
public class Sessao {
    private String codigo;
    private Filme filme;
    private Sala sala;
    private String horario;
    private String data;
    private User usuario;

    public Sessao(Filme filme, Sala sala, String horario, String data, User usuario) {
        this.codigo = data+horario+filme.getId();
        this.filme = filme;
        this.sala = sala;
        this.horario = horario;
        this.data = data;
        this.usuario = usuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

public class Propaganda {
    private int codigo;
    private String anunciante;
    private Filme filme;
    private User usuario;

        public Propaganda(String anunciante, Filme filme, User usuario) {
            this.anunciante = anunciante;
            this.filme = filme;
            this.usuario = usuario;
        } 
    
	public String getAnunciante() {
		return anunciante;
	}
	public void setAnunciante(String anunciante) {
		this.anunciante = anunciante;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Filme getFilme() {
		return filme;
	}
	public void setFilme(Filme filme) {
		this.filme = filme;
	}

        public User getUsuario() {
            return usuario;
        }

        public void setUsuario(User usuario) {
            this.usuario = usuario;
        }
        
}


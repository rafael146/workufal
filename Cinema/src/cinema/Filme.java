/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import se.Fato;

/**
 *
 * @author marcus
 */
public class Filme extends Fato {
    private int id;//
    private String tituloPortugues;//
    private String tituloEstrangeiro;//
    private String tipo;//
    private String genero;//
    private int duracao;//
    private String classificacaoindicativa;//
    private String sinopse;//
    private String anoDeLancamento;//
    private String diretor;//
    private User usuario;

    public Filme(int id,String tituloPortugues, String tituloEstrangeiro, String tipo,
            String genero, int duracao, String classificacaoindicativa, String sinopse,
            String anoDeLancamento, String diretor,User usuario) {
        this.tituloPortugues = tituloPortugues;
        this.tituloEstrangeiro = tituloEstrangeiro;
        this.tipo = tipo;
        this.genero = genero;
        this.duracao = duracao;
        this.classificacaoindicativa = classificacaoindicativa;
        this.sinopse = sinopse;
        this.anoDeLancamento = anoDeLancamento;
        this.diretor = diretor;
        this.usuario=usuario;
        this.id = id;
    }
    
    public Filme(String tituloPortugues, String tituloEstrangeiro, String tipo,
            String genero, int duracao, String classificacaoindicativa, String sinopse,
            String anoDeLancamento, String diretor,User usuario) {
        this.tituloPortugues = tituloPortugues;
        this.tituloEstrangeiro = tituloEstrangeiro;
        this.tipo = tipo;
        this.genero = genero;
        this.duracao = duracao;
        this.classificacaoindicativa = classificacaoindicativa;
        this.sinopse = sinopse;
        this.anoDeLancamento = anoDeLancamento;
        this.diretor = diretor;
        this.usuario=usuario;
    }

    public Filme(int id, String tituloptbr, String genero, String tipo,
			int duracao, String classificacao, User usuario) {
    	this.id=id;
    	this.tituloPortugues=tituloptbr;
    	this.genero=genero;
    	this.tipo=tipo;
    	this.duracao=duracao;
    	this.classificacaoindicativa=classificacao;
    	this.usuario=usuario;
		// TODO Auto-generated constructor stub
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloPortugues() {
        return tituloPortugues;
    }

    public void setTituloPortugues(String tituloPortugues) {
        this.tituloPortugues = tituloPortugues;
    }

    public String getTituloEstrangeiro() {
        return tituloEstrangeiro;
    }

    public void setTituloEstrangeiro(String tituloEstrangeiro) {
        this.tituloEstrangeiro = tituloEstrangeiro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getClassificacaoindicativa() {
        return classificacaoindicativa;
    }

    public void setClassificacaoindicativa(String classificacaoindicativa) {
        this.classificacaoindicativa = classificacaoindicativa;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public void setAnoDeLancamento(String anoDeLancamento) {
        this.anoDeLancamento = anoDeLancamento;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

	@Override
	public Fato perguntar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fato avaliar(Fato[] premissas) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}

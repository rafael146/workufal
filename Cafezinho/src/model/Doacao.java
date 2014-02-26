package model;

import javafx.beans.property.SimpleStringProperty;

public class Doacao {
	private Bolsista bolsista;
	private SimpleStringProperty descricao;
	private Rotina rotina;
	
	public Doacao(Bolsista bolsista, String descricao, Rotina rotina) {
		this.bolsista=bolsista;
		this.descricao=new SimpleStringProperty(descricao);
		this.rotina=rotina;
	}

	public String getDescricao() {
		return descricao.get();
	}
	public void setDescricao(String descricao) {
		this.descricao.set(descricao);
	}

	public Bolsista getBolsista() {
		return bolsista;
	}

	public void setBolsista(Bolsista bolsista) {
		this.bolsista = bolsista;
	}

	public Rotina getRotina() {
		return rotina;
	}

	public void setRotina(Rotina rotina) {
		this.rotina = rotina;
	}
	
}

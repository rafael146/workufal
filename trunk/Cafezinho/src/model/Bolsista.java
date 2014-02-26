package model;

import javafx.beans.property.SimpleStringProperty;

public class Bolsista {
	private SimpleStringProperty nome;
	private String email;
	
	public Bolsista(String nome, String email) {
		this.nome=new SimpleStringProperty(nome);
		this.email=email;
	}
	
	public String getNome() {
		return nome.get();
	}
	public void setNome(String nome) {
		this.nome.set(nome);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return nome.get();
	}
	
}

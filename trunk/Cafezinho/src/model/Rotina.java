package model;

import java.util.Date;

public class Rotina {
	private double id;
	private Date dataInicial;
	private Date dataFinal;
	
	public Rotina(){}
	
	public Rotina(double x) {
		this.id = x;
	}
	
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
}

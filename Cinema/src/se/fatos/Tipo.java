package se.fatos;


import javax.swing.JOptionPane;

import se.Fato;
import se.Memoria;

public class Tipo extends Fato {

	@Override
	public Fato perguntar() {
		String tipo;
		tipo="'%"+JOptionPane.showInputDialog("Tipo: 2D ou 3D")+"%'";
		setValor(tipo);
		System.out.println(tipo);
		Memoria.getInstance().addFato(this);
		return this;
	}

	@Override
	public Fato avaliar(Fato[] premissas) {
		// TODO Auto-generated method stub
		return null;
	}

}

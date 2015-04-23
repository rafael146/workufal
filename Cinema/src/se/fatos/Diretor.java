package se.fatos;

import javax.swing.JOptionPane;

import se.Fato;

public class Diretor extends Fato {

	@Override
	public Fato perguntar() {
		String diretor;
		diretor=JOptionPane.showInputDialog("Diretor");
		setValor(diretor);
		return this;
	}

	@Override
	public Fato avaliar(Fato[] premissas) {
		// TODO Auto-generated method stub
		return null;
	}

}

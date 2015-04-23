package se.fatos;

import javax.swing.JOptionPane;

import se.Fato;
import se.Memoria;

public class Idade extends Fato {

	@Override
	public Fato perguntar() {
		String idade;
		idade=(JOptionPane.showInputDialog("Digite a idade: "));
		setValor(idade);
		Memoria.getInstance().addFato(this);
		System.out.println(idade);
		return this;
	}

	@Override
	public Fato avaliar(Fato[] premissas) {
		// TODO Auto-generated method stub
		Memoria.getInstance().addFato(this);
		
		return this;
	}
	

}

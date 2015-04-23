package se.fatos;

import javax.swing.JOptionPane;

import se.Fato;
import se.Memoria;

public class Classificacao extends Fato {

	@Override
	public Fato perguntar() {
		String classificacao;
		classificacao=JOptionPane.showInputDialog("Digite a classificacao: ");
		setValor(classificacao);
		return this;
	}

	@Override
	public Fato avaliar(Fato[] premissas) {
		String classifica=null;
		int idade=Integer.parseInt(premissas[0].getValor());
		if (idade<12){
			classifica="Livre";
		}else if(idade <14){
			classifica="Maior 12";
		}else if(idade < 16){
			classifica="Maior 14";
		}else if(idade <18){
			classifica="Maior 16";
		}
		else{
			classifica="Maior 18";
		}
		setValor(classifica);
		Memoria.getInstance().addFato(this);
		return this;
	}

}

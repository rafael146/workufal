package se.fatos;



import se.Fato;
import se.Memoria;

public class Genero extends Fato {

	@Override
	public Fato perguntar() {
		return null;
	}

	@Override
	public Fato avaliar(Fato[] premissas) {
		String classificacao=null;
		String genero=null;
		classificacao= premissas[0].getValor();
		switch (classificacao) {
		case "Livre":
			genero="(GENERO LIKE  '%Animacao%' OR GENERO LIKE  '%Comedia%' OR GENERO LIKE  '%Infantil%')";
			break;
		case "Maior 12":
			genero="(GENERO LIKE  '%Animacao%' OR GENERO LIKE  '%Comedia%' OR GENERO LIKE  '%Infantil%' OR GENERO LIKE '%Aventura%')";
			break;
		case "Maior 14":
			genero="(GENERO LIKE  '%Animacao%' OR GENERO LIKE  '%Comedia%' OR GENERO LIKE  '%Infantil%' OR GENERO LIKE '%Aventura%' OR GENERO LIKE '%Romance%' OR GENERO LIKE '%Acao%')";
			break;
		case "Maior 16":
			genero="(GENERO LIKE  '%Animacao%' OR GENERO LIKE  '%Comedia%' OR GENERO LIKE  '%Infantil%' OR GENERO LIKE '%Aventura%' OR GENERO LIKE '%Romance%' OR GENERO LIKE '%Acao%' OR GENERO LIKE '%Ficcao%')";
		case "Maior 18":
			genero="(GENERO LIKE  '%Animacao%' OR GENERO LIKE  '%Comedia%' OR GENERO LIKE  '%Infantil%' OR GENERO LIKE '%Aventura%' OR GENERO LIKE '%Romance%' OR GENERO LIKE '%Acao%' OR GENERO LIKE '%Ficcao%' OR GENERO LIKE '%Terror%' OR GENERO LIKE '%Pornográfico%')";
		default:
			break;
		}
		setValor(genero);
		Memoria.getInstance().addFato(this);
		//System.out.println(genero);
		return this;
	}

}

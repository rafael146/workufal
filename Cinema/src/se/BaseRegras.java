package se;

import java.util.HashMap;
import java.util.Map;

import se.fatos.Classificacao;
import se.fatos.Genero;
import se.fatos.Idade;
import se.fatos.Tipo;

public class BaseRegras implements Base {

	
	Map<Class<?extends Fato>, Regra> regras = new HashMap<>();

	@Override
	public Regra busca(Fato conclusao) {
		return regras.get(conclusao.getClass());
	}
	
	/*Genero^ipo->Lista de Filmes
	 * Classificacao -> Genero
	 * Idade->Classificacao
	 */
	
	private BaseRegras() {
		regras.put(ListadeFilmes.class,new Regra(new ListadeFilmes(), new Fato[]{new Genero(), new Tipo()}));
		regras.put(Genero.class,new Regra(new Genero(), new Classificacao()));
		regras.put(Classificacao.class,new Regra(new Classificacao(), new Idade()));
	}
	
	
private static BaseRegras instance;
	
	public static BaseRegras getInstance() {
		if(instance == null) 
			instance = new BaseRegras();
		return instance;
	}


}

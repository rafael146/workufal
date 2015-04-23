package se;

import java.util.LinkedList;
import java.util.List;

public class Memoria {
	List<Fato> fatos = new LinkedList<>();
	
	public void addFato(Fato f){
		fatos.add(f);
	}
	public boolean contem(Fato f){
		for (Fato e : fatos) {
			if (e.equals(f)){
				return true;
			}
		}
		return false;
		
	}
	public Fato getFato(Fato f){
		for (Fato e : fatos) {
			if (e.equals(f)){
				return e;
			}
		}
		return null;
	
	}
	public Fato[] getFatos(Fato... premissas) {
		int l = premissas.length;
		for(int i = 0; i < l; i++) {
			premissas[i] = getFato(premissas[i]);
		}
		return premissas;
	}
	public void limpar() {
		fatos.clear();
	}
	
	private Memoria() {	}
	
	private static Memoria instance;
	
	public static Memoria getInstance() {
		if(instance == null) {
			instance = new Memoria();
		}
		return instance;
	}
}

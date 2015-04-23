package se;

public class Regra {
	private Fato[] premissas;	
	private Fato conclusao;
	private static Memoria memoria = Memoria.getInstance();
	
	public Regra(Fato conclusao, Fato... premissas) {
		this.conclusao = conclusao;
		this.premissas = premissas;
	}
	
	
	public Fato[] getPremissas() {
		return premissas;
	}
	
	
	public Fato avaliar() {
		return conclusao.avaliar(memoria.getFatos(premissas));
	}
	
}
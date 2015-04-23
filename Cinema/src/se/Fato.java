package se;

public abstract class Fato {
	private String valor;
	
	public  abstract Fato perguntar();

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public abstract Fato avaliar(Fato[] premissas);
	
	@Override
	public boolean equals(Object obj) {
		return getClass().isInstance(obj);
	}

}

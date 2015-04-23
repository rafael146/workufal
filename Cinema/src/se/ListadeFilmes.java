package se;

public class ListadeFilmes extends Fato {

	@Override
	public Fato perguntar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fato avaliar(Fato[] premissas) {
		String genero=premissas[0].getValor();
		String tipo=premissas[1].getValor();
		//String diretor=premissas[2].getValor();
		System.out.println("tipo "+tipo);
		String sql="select *  from CinemaDB.FILME where "+genero
			+" AND TIPOFORMATO LIKE"+tipo+";";//" AND DIRETOR="+diretor;
		//System.out.println(sql);
		setValor(sql);
		Memoria.getInstance().addFato(this);
		return this;
	}
       
	
	
}

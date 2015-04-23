package se;


public class MotorInferencia implements Motor{
	
	private final Memoria memoria = Memoria.getInstance();
	private final BaseRegras base = BaseRegras.getInstance();
	
	@Override
	public Fato inferir(Fato fato) {
		System.out.println("Inferindo");
		if(memoria.contem(fato)) {
			System.out.println(fato.getValor());
			return memoria.getFato(fato);
		}
		System.out.println("Fato não está na memória");
		Regra regra = base.busca(fato);
		if(regra != null) {
			System.out.println("Fato encontrado em uma conclusão");
			
			for (Fato ft : regra.getPremissas()) {
				inferir(ft);
			}
			return regra.avaliar();
		} else {
			System.out.println("perguntar fato");
			return fato.perguntar();
		}
	}

}
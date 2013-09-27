/**
 * @author Leonardo Maxwell
 * @author Danylo Souza
 */

package filaencadeadajava;

public class FilaEncadeada {
	private Elemento prim;
	private Elemento fim;
	
	public FilaEncadeada(Elemento e){
		prim = e;
		fim = e;
	}
	
	public Boolean isEmpty() {
		return prim == null;
	}
	
	public void enfileira(Elemento n){
		if(isEmpty()){
			prim = n;
			fim = n;
		}else{
			fim.setProx(n);
		}
		
	}
	
	public Elemento desenfileira(){
		if(!isEmpty()){
			prim = prim.getProx();
		}
		return prim;
	}
	
	public Elemento verInicio(){
		return prim;
	}
}


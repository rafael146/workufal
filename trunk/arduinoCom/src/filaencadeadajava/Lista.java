package filaencadeadajava;

public class Lista {
	private FilaEncadeada prim;
	
	public Boolean isEmpty() {
		return prim == null;
	}
	public  FilaEncadeada enfileira(FilaEncadeada n){
		if(isEmpty()){
			prim = n;
		}else{
			FilaEncadeada temp = prim;
			while(temp.getProx() != null){
				temp = temp.getProx();
			}temp.setProx(n);
		}
		return n;
	}
	
	public FilaEncadeada desenfileira(){
		if(!isEmpty()){
			FilaEncadeada temp = prim;
			prim = prim.getProx();
		}
		return prim;
	}
	
	public FilaEncadeada verInicio(){
		return prim;
	}
}

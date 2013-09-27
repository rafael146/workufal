/**
 * 
 */
package filaencadeadajava;

/**
 * @author Leonardo Maxwell
 * @author Danylo Souza
 */
public class Elemento{
	
	private int num;
	private Elemento prox;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Elemento getProx() {
		return prox;
	}
	public void setProx(Elemento prox) {
		this.prox = prox;
	}
	
	public Elemento(int num) {
		this.num = num;
		prox = null;
	}
	
}


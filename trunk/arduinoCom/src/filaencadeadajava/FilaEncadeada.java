/**
 * 
 */
package filaencadeadajava;

/**
 * @author zannenunes
 *
 */
public class FilaEncadeada{
	
	private int num;
	private FilaEncadeada prox;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public FilaEncadeada getProx() {
		return prox;
	}
	public void setProx(FilaEncadeada prox) {
		this.prox = prox;
	}
	
	public FilaEncadeada(int num, FilaEncadeada prox) {
		this.num = num;
		this.prox = prox;
	}
	
}


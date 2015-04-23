package cinema;

public class Ingresso {
	private int codigo;
	private double valor;
	private Sessao sessao;
	private String forma_Pagamento;
	private User user;
        private String cadeira;

    public Ingresso(double valor, Sessao sessao, String forma_Pagamento, User user, String cadeira) {
        this.valor = valor;
        this.sessao = sessao;
        this.forma_Pagamento = forma_Pagamento;
        this.user = user;
        this.cadeira = cadeira;
    }

    public Ingresso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public String getForma_Pagamento() {
        return forma_Pagamento;
    }

    public void setForma_Pagamento(String forma_Pagamento) {
        this.forma_Pagamento = forma_Pagamento;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCadeira() {
        return cadeira;
    }

    public void setCadeira(String cadeira) {
        this.cadeira = cadeira;
    }
    
    
        
}
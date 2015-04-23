package cinema;


public  class User {
	private String login;
	private String senha;
	private Funcionario funcionario;
        
	public User(String login, String senha,Funcionario funcionario) {
		this.login = login;
		this.senha = senha;
                this.funcionario = funcionario;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
    public static String criptografarkey(char[] key) {  
    String encoding = " <abcdefghijklmnopqrstuvwxyzçéáíúóãõABCDEFGHIJKLMNOPQRSTUVWXYZÇÁÉÓÍÚÃÕ1234567890.;:?,º]}§[{ª!@#$%&*()_+-=\\/|\'\">";   
      		String key_Encript="";  
      		for (int i = 0; i < key.length; i++) {  
      			int posicao = encoding.indexOf(key[i])+2;  
      			if (encoding.length() <= posicao) {  
      				posicao = posicao - encoding.length();  
        	   	}
        	   		key_Encript = key_Encript + encoding.charAt(posicao);  
           	}  
      		return "$@"+key_Encript+"%*&";  
    }
}
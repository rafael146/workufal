package datebase;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Conexao {
	 protected Connection CONNECTION;
	 protected PreparedStatement PREPARED_STATEMENT;
	 protected ResultSet RESULT_SET;
	 protected  InfoDB info;
	 protected String sql;

	    public boolean conectar(){
	    	try {
				Class.forName(info.getDRIVER());
				CONNECTION = (Connection) DriverManager.getConnection(info.getURL(), info.getUSER(), info.getPASSWORD());
			} catch (Exception e) {
				System.out.println("Não conectado");
			}
			return false;
	    }
	    protected void executarSQL(String comandoSql) {
	        try {
	            conectar();
	            PREPARED_STATEMENT = CONNECTION.prepareStatement(comandoSql);
	            PREPARED_STATEMENT.executeUpdate();
	           
	            finalizarDataBase();
	        } catch (SQLException ex) {
	            System.out.println("Comando SQL nao executado:" + ex.getMessage());
	        } catch (NullPointerException npe) {
	            System.out.println("Conexao inexistente. " + npe.getMessage());
	        }
	    }
	    protected void finalizarDataBase() {
	        try {
	            CONNECTION.close();
	            PREPARED_STATEMENT.close();
	        } catch (SQLException ex) {
	            System.out.println("Conexao nao finalizada.");
	        }
	    }
	    

	    public abstract ArrayList<String> getXML();
	    public long exportarXML(String path) {
	        long tempoInicio = 0;
	        conectar();
	        try {
	        	
	            File arq = new File(path + "/pessoa.xml");
	            if (!arq.exists()) {
	                arq.createNewFile();
	            }
	            FileWriter arqEscreve = new FileWriter(arq, false);
	            BufferedWriter arqBuffer = new BufferedWriter(
	                    new OutputStreamWriter(new FileOutputStream(arq), "UTF-8"));

	            tempoInicio = System.currentTimeMillis();
	            ArrayList<String> dados = getXML();
	            tempoInicio = System.currentTimeMillis() - tempoInicio;

	            for (String x : dados) {
	                arqBuffer.write(x);
	            }

	            arqBuffer.close();
	            arqEscreve.close();
	        } catch (IOException ex) {
	        }
	        return tempoInicio;

	    }
	    
	    public abstract void createTable();
	    public abstract long importarXML(String path);
	    
	    


}

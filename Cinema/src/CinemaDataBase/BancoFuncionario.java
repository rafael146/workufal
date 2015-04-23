/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;


import cinema.Endereco;
import cinema.Funcionario;
import cinema.User;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author marcus-pc
 */
public class BancoFuncionario extends BDConection implements SaveDB<Funcionario> {
    
   
    @Override
    public void saveObject(Funcionario o) {
        try {
            Funcionario funcionario = o;
             BDConected();
             String query = "insert into FUNCIONARIO values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             pstm = (PreparedStatement) connection.prepareStatement(query);
             pstm.setString(1,funcionario.getNome());
             pstm.setString(2,funcionario.getCpf());
             pstm.setString(3,funcionario.getRg());
             pstm.setString(4,funcionario.getEmail());
             pstm.setString(5,funcionario.getEndereco().getLogradouro());
             pstm.setString(6,funcionario.getEndereco().getBairro());    
             pstm.setInt(7,funcionario.getEndereco().getNumero());
             pstm.setString(8,funcionario.getEndereco().getCidade());
             pstm.setString(9,funcionario.getEndereco().getCep());
             pstm.setString(10,funcionario.getEndereco().getEstado());
             pstm.setString(11,funcionario.getObservacao());
             pstm.setString(12,funcionario.getFone());
             pstm.setBoolean(13,funcionario.getTemLogin());
             pstm.setString(14,funcionario.getFuncao());
             if(funcionario.getUsuario()==null){
                 pstm.setString(15,"admin");
             }else{
                 pstm.setString(15,funcionario.getUsuario().getLogin());
             }
             pstm.execute();
             BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setObject(Funcionario o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Funcionario getObject(String o) {
        try {
             String id = o;
             BDConected();
             String query = "select * from FUNCIONARIO where idFUNCIONARIO=?";
             java.sql.PreparedStatement stmt = connection.prepareStatement(query);
             stmt.setString(1, id);
             stmt.execute();
             ResultSet rs = stmt.executeQuery();
             rs.next();
                 int idfuncionario = rs.getInt("idFUNCIONARIO");
                 String nome = rs.getString("nome");//nome da coluna
                 String logradouro= rs.getString("logradouro");
                 int numero= Integer.parseInt(rs.getString("endrnumero"));
                 String bairro= rs.getString("bairro");
                 String cpf= rs.getString("cpf");
                 String rg= rs.getString("rg");
                 String cep= rs.getString("cep");
                 String email= rs.getString("email");
                 String telefone= rs.getString("fone");
                 String observacao= rs.getString("observacao");
                 String estado= rs.getString("estado");
                 String cidade= rs.getString("cidade");
                 String funcao= rs.getString("funcao");
                 boolean temLogin = rs.getBoolean("TEMLOGIN");
                 User usuario;
                 if(rs.getInt("idFUNCIONARIO")==1){//quando a chamada se referir ao administrador do sistema
                     usuario = null;
                 }else{
                     usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                 }
                 Funcionario c2 =  new Funcionario(idfuncionario,nome, cpf, rg, email, new Endereco(logradouro, bairro,numero,  cidade, cep,estado), observacao, telefone, funcao, usuario,temLogin);
                 BdDesconected();
                 return c2;
        } catch (SQLException ex) {
            Logger.getLogger(BancoFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Funcionario> searchObjects() {
        try {
            BDConected();
             java.sql.PreparedStatement stmt = connection.prepareStatement("select * from FUNCIONARIO");
             ResultSet rs = stmt.executeQuery();
             ArrayList<Funcionario> lista = new ArrayList();
             while (rs.next()){
                 int idfuncionario = rs.getInt("idFUNCIONARIO");
                 String nome = rs.getString("nome");//nome da coluna
                 String logradouro= rs.getString("logradouro");
                 int numero= Integer.parseInt(rs.getString("endrnumero"));
                 String bairro= rs.getString("bairro");
                 String cpf= rs.getString("cpf");
                 String rg= rs.getString("rg");
                 String cep= rs.getString("cep");
                 String email= rs.getString("email");
                 String telefone= rs.getString("fone");
                 String observacao= rs.getString("observacao");
                 String estado= rs.getString("estado");
                 String cidade= rs.getString("cidade");
                 String funcao= rs.getString("funcao");
                 User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                 boolean temLogin = rs.getBoolean("TEMLOGIN");
                 Funcionario c2 =  new Funcionario(idfuncionario,nome, cpf, rg, email, new Endereco(logradouro, bairro,numero,  cidade, cep,estado), observacao, telefone, funcao, usuario,temLogin);
                 lista.add(c2);
             }
             rs.close();
             BdDesconected();
             return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BancoFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteobject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void alterarTemLoginFuncionario(Funcionario funcionario) {
        try {
            BDConected();
            String query = "UPDATE FUNCIONARIO set TEMLOGIN=? where idFUNCIONARIO=?";
            java.sql.PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setBoolean(1, true);
            stmt.setInt(2, funcionario.getId());
            stmt.execute();
            BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


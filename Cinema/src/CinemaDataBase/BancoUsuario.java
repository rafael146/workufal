/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

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
 * @author henrique
 */
public class BancoUsuario extends BDConection implements SaveDB<User>{

    @Override
    public void saveObject(User o) {
        try {
            User usuario = o;
            BDConected();
            String query = "insert into USUARIO values(?,?,?)";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.setString(1,usuario.getLogin());
            String senha = new String(usuario.getSenha());
            pstm.setString(2,senha);
            pstm.setInt(3,usuario.getFuncionario().getId());
            pstm.execute();
            BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setObject(User o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getObject(String o) {
        try {
            String login = o;
            BDConected();
            String query = "select * from USUARIO where LOGIN=?";
            java.sql.PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, login);
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String senha = rs.getString("SENHA").toString();
            Funcionario id2 = new BancoFuncionario().getObject(rs.getString("idFUNCIONARIO"));
            User c2 =  new User(login,senha,id2);
            rs.close();
            BdDesconected();
            return c2;
        } catch (SQLException ex) {
            Logger.getLogger(BancoUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<User> searchObjects() {
        try {
            BDConected();
            java.sql.PreparedStatement stmt = connection.prepareStatement("select * from USUARIO");
            ResultSet rs = stmt.executeQuery();
            ArrayList<User> lista = new ArrayList();
            while (rs.next()){
                String login = rs.getString("LOGIN");//nome da coluna
                String senha = rs.getString("SENHA").toString();
                Funcionario id = new BancoFuncionario().getObject(rs.getString("idFUNCIONARIO"));
                User c2 =  new User(login,senha,id);
                lista.add(c2);
            }
            rs.close();
            stmt.close();
            connection.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BancoUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteobject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

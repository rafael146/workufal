/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

import cinema.Cliente;
import cinema.User;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author henrique
 */
public class BancoCliente extends BDConection implements SaveDB<Cliente>{

    @Override
    public void saveObject(Cliente o) {
        try {
            Cliente cliente = o;
            BDConected();
            String query = "insert into CLIENTE values(null,?,?,?,?)";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.setString(1,cliente.getNome());
            pstm.setString(2,cliente.getEmail());
            pstm.setString(3,cliente.getGenero());
            pstm.setString(4,cliente.getUsuario().getLogin());
            pstm.execute();
            BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   @Override
    public ArrayList<Cliente> searchObjects() {
        try {
            BDConected();
           java.sql.PreparedStatement stmt = connection.prepareStatement("select * from CLIENTE");
           ResultSet rs = stmt.executeQuery();
           ArrayList<Cliente> lista = new ArrayList();
           while (rs.next()){
               String nome = rs.getString("NOME");//nome da coluna
               String email = rs.getString("EMAIL");
               String genero = rs.getString("GENERO");
               User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
               Cliente c2 =  new Cliente(nome,email,usuario,genero);
               lista.add(c2);
           }
           rs.close();
           stmt.close();
           connection.close();
           return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BancoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }

    @Override
    public void deleteobject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public void setObject(Cliente o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cliente getObject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

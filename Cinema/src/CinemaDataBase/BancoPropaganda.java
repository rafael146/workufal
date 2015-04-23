/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

import cinema.Filme;
import cinema.Propaganda;
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
 * @author marcus
 */
public class BancoPropaganda extends BDConection implements SaveDB<Propaganda>{

    @Override
    public void saveObject(Propaganda o) {
        try {
            Propaganda propaganda = o;
            BDConected();
            String query = "insert into PROPAGANDA values(null,?,?,?)";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.setString(1, propaganda.getAnunciante());
            pstm.setInt(2, propaganda.getFilme().getId());
            pstm.setString(3, propaganda.getUsuario().getLogin());
            pstm.execute();
            BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoPropaganda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Propaganda> searchObjects() {
        try {
            BDConected();
            java.sql.PreparedStatement stmt = connection.prepareStatement("select * from PROPAGANDA");
            ResultSet rs = stmt.executeQuery();
            ArrayList<Propaganda> lista = new ArrayList();
            while (rs.next()){
                String anunciante = rs.getString("ANUNCIANTE");//nome da coluna
                Filme filme = new BancoFilme().getObject(rs.getString("idFILME"));
                User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                Propaganda c2 =  new Propaganda(anunciante,filme,usuario);
                lista.add(c2);
            }
            rs.close();
            stmt.close();
            connection.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BancoPropaganda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void setObject(Propaganda o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Propaganda getObject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    @Override
    public void deleteobject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

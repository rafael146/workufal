/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

import cinema.Sala;
import cinema.User;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcus
 */
public class BancoSala extends BDConection implements SaveDB<Sala>{

    @Override
    public void saveObject(Sala o) {
        try {
            Sala sala = o;
            BDConected();
            String query = "insert into SALA values(?,?,?,?)";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.setInt(1,sala.getNumero());
            pstm.setInt(2,sala.getCapacidade());
            pstm.setString(3,sala.getTipo());
            pstm.setString(4,sala.getUsuario().getLogin());
            pstm.execute();
            BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoSala.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setObject(Sala o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sala getObject(String o) {
        try {
            String id = o;
            BDConected();
            String query = "select * from SALA where NUMEROSALA=?";
            java.sql.PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            rs.next();
                int numero = Integer.parseInt(rs.getString("NUMEROSALA"));
                int capacidade = Integer.parseInt(rs.getString("CAPACIDADE"));//nome da coluna
                String tipo = rs.getString("TIPO");
                User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                Sala c2 =  new Sala(numero,capacidade,tipo,usuario);
                BdDesconected();
                return c2;
        } catch (SQLException ex) {
            Logger.getLogger(BancoSala.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Sala> searchObjects() {
        try {
            BDConected();
           java.sql.PreparedStatement stmt = connection.prepareStatement("select * from SALA");
           ResultSet rs = stmt.executeQuery();
           ArrayList<Sala> lista = new ArrayList();
           while (rs.next()){
               int numero = Integer.parseInt(rs.getString("NUMEROSALA"));
               int capacidade = Integer.parseInt(rs.getString("CAPACIDADE"));//nome da coluna
               String tipo = rs.getString("TIPO");
               User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
               Sala c2 =  new Sala(numero,capacidade,tipo,usuario);
               lista.add(c2);
           }
           rs.close();
           stmt.close();
           connection.close();
           return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BancoSala.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }

    @Override
    public void deleteobject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}

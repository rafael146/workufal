/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

import cinema.Funcionario;
import cinema.Ingresso;
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
public class BancoIngresso extends BDConection implements SaveDB<Ingresso>{
 
    @Override
    public void saveObject(Ingresso o) {
        try {
           Ingresso ingresso = o;
           BDConected();
           String query = "insert into INGRESSO values(null,?,?,?,?,?)";
           pstm = (PreparedStatement) connection.prepareStatement(query);
           pstm.setDouble(1, ingresso.getValor());
           pstm.setString(2, ingresso.getSessao().getCodigo());
           pstm.setString(3, ingresso.getForma_Pagamento());
           pstm.setString(4, ingresso.getUser().getLogin());
           pstm.setString(5, ingresso.getCadeira());
           pstm.execute();
           BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoIngresso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setObject(Ingresso o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ingresso getObject(String o) {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Ingresso> searchObjects() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteobject(String o) {
        try {
            String id = o;
            BDConected();
            String query = "delete from INGRESSO where CODINGRESSO=?;";
            java.sql.PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BancoIngresso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

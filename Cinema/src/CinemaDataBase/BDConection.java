/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//PreparateStatement 
//save (parametro Conectio  +nome da conexão)
package CinemaDataBase;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class BDConection {
    
    
    Connection connection; 
    PreparedStatement pstm;
    
    public  void BDConected(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/CinemaDB","root","chrivmiB123@");
            //System.out.print("Connected Sucefull");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BDConection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Conecção Falhada");
        }
    }
 
    public void  BdDesconected(){
        try{
        if (connection != null){
            connection.close();
        }
        if (pstm != null){
            pstm.close();
        }
        }catch(SQLException e){
            e.getMessage();
        }
    }
    
    
    
}

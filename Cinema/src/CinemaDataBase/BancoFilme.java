/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

import cinema.Filme;
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
public class BancoFilme extends BDConection implements SaveDB<Filme>{

    @Override
    public void saveObject(Filme o) {
        try {
            Filme filme;
            filme = (Filme) o;
            BDConected();
            String query = "insert into FILME values(null,?,?,?,?,?,?,?,?,?,?)";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.setString(1,filme.getTituloPortugues());
            pstm.setString(2,filme.getTituloEstrangeiro());
            pstm.setString(3,filme.getGenero());
            pstm.setString(4,filme.getSinopse());
            pstm.setString(5,filme.getDiretor());
            pstm.setString(6,filme.getAnoDeLancamento());
            pstm.setString(7,filme.getClassificacaoindicativa());
            pstm.setInt(8,filme.getDuracao());
            pstm.setString(9,filme.getUsuario().getLogin());
            pstm.setString(10,filme.getTipo());
            pstm.execute();
            BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoFilme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public Filme getObject(String o) {
        String id = o;
        try {
            BDConected();
           String query = "select * from FILME where idFILME=?";
           java.sql.PreparedStatement stmt = connection.prepareStatement(query);
           stmt.setString(1, id);
           stmt.execute();
           ResultSet rs = stmt.executeQuery();
           rs.next();
           int idFILME = rs.getInt("idFILME");
           String tituloptbr = rs.getString("TITULOPTBR");//nome da coluna
           String tituloorgn = rs.getString("TITULOORGN");
           String genero = rs.getString("GENERO");
           String sinopse = rs.getString("SINOPSE");
           String diretor = rs.getString("DIRETOR");
           String anodelancamento = rs.getString("ANOLANCAMENTO");
           String classificacao = rs.getString("CLASSINDICATIVA");
           int duracao = rs.getInt("DURACAO");
           String tipo = rs.getString("TIPOFORMATO");

           User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
           Filme c2 =  new Filme(idFILME,tituloptbr,tituloorgn,tipo,genero,duracao,classificacao,sinopse,anodelancamento,diretor,usuario);
           return c2;
        } catch (SQLException ex) {
            Logger.getLogger(BancoFilme.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Filme> searchObjects() {
        try {
            BDConected();
            java.sql.PreparedStatement stmt = connection.prepareStatement("select * from FILME");
            ResultSet rs = stmt.executeQuery();
            ArrayList<Filme> lista = new ArrayList();
            while (rs.next()){
                int id = rs.getInt("idFILME");
                String tituloptbr = rs.getString("TITULOPTBR");//nome da coluna
                String tituloorgn = rs.getString("TITULOORGN");
                String genero = rs.getString("GENERO");
                String sinopse = rs.getString("SINOPSE");
                String diretor = rs.getString("DIRETOR");
                String anodelancamento = rs.getString("ANOLANCAMENTO");
                String classificacao = rs.getString("CLASSINDICATIVA");
                int duracao = rs.getInt("DURACAO");
                String tipo = rs.getString("TIPOFORMATO");
                
                User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                Filme c2 =  new Filme(id,tituloptbr,tituloorgn,tipo,genero,duracao,classificacao,sinopse,anodelancamento,diretor,usuario);
                lista.add(c2);
            }
            rs.close();
            stmt.close();
            connection.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BancoFilme.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }

    public ArrayList<Filme> searchObjects(String sql) {
        try {
            BDConected();
            java.sql.PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Filme> lista = new ArrayList<Filme>();
            while (rs.next()){
                int id = rs.getInt("idFILME");
                String tituloptbr = rs.getString("TITULOPTBR");//nome da coluna
                String genero = rs.getString("GENERO");
                String classificacao = rs.getString("CLASSINDICATIVA");
                int duracao = rs.getInt("DURACAO");
                String tipo = rs.getString("TIPOFORMATO");
                
                User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                System.out.println(tituloptbr);
                Filme c2 =  new Filme(id,tituloptbr,genero,tipo, duracao,classificacao, usuario);
                lista.add(c2);
            }
            rs.close();
            stmt.close();
            connection.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BancoFilme.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }

    @Override
    public void deleteobject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    @Override
    public void setObject(Filme o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

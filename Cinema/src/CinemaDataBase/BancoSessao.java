/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

import cinema.Filme;
import cinema.Sala;
import cinema.Sessao;
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
public class BancoSessao extends BDConection implements SaveDB<Sessao>{

    @Override
    public void saveObject(Sessao o) {
        try {
            Sessao sessao = o;
            BDConected();
            String query = "insert into SESSAO values(?,?,?,?,?,?)";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.setString(1, sessao.getCodigo());
            System.out.println(sessao.getCodigo());
            pstm.setInt(2, sessao.getFilme().getId());
            pstm.setInt(3, sessao.getSala().getNumero());
            pstm.setString(4, sessao.getData());
            pstm.setString(5, sessao.getHorario());
            pstm.setString(6, sessao.getUsuario().getLogin());
            pstm.execute();
            BdDesconected();
        } catch (SQLException ex) {
            Logger.getLogger(BancoSessao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setObject(Sessao o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sessao getObject(String o) {
        try {
            BDConected();
            String query = "select * from SESSAO where CODSESSAO=?";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.setString(1, o);
            pstm.execute();
            ResultSet rs = pstm.executeQuery();
            java.sql.PreparedStatement stmt = connection.prepareStatement(query);
            pstm.setString(1, o);
            pstm.execute();
            rs.next();
                Filme filme = new BancoFilme().getObject(rs.getString("idFILME"));
                Sala sala = new BancoSala().getObject(rs.getString("NUMEROSALA"));//nome da coluna
                String data = rs.getString("DATA");
                String horario = rs.getString("HORARIO");
                User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                Sessao sessao = new Sessao(filme,sala,horario,data,usuario);
                BdDesconected();
            return sessao;
        } catch (SQLException ex) {
            System.out.println("estrou no erro");
            Logger.getLogger(BancoSessao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Sessao> searchObjects() {
        try {
            BDConected();
            String query = "select * from SESSAO";
            pstm = (PreparedStatement) connection.prepareStatement(query);
            pstm.execute();
            ResultSet rs = pstm.executeQuery();
            ArrayList<Sessao> sessoes = new ArrayList();
            while (rs.next()){
                Filme filme = new BancoFilme().getObject(rs.getString("idFILME"));
                Sala sala = new BancoSala().getObject(rs.getString("NUMEROSALA"));//nome da coluna
                String data = rs.getString("DATA");
                String horario = rs.getString("HORARIO");
                User usuario = new BancoUsuario().getObject(rs.getString("LOGIN"));
                Sessao sessao = new Sessao(filme,sala,horario,data,usuario);
                sessoes.add(sessao);
            }
            return sessoes;
        } catch (SQLException ex) {
            Logger.getLogger(BancoSessao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteobject(String o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

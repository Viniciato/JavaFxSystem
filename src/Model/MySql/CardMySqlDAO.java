package Model.MySql;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public class CardMySqlDAO extends MysqlDatabase implements CardDAO {
    @Override
    public void create(Card card) {
    }

    @Override
    public void delete(Card card) {

    }

    @Override
    public void update(Card card) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("UPDATE cards SET status = ? WHERE id = ?");
            stm.setInt(1, card.getStatus());
            stm.setInt(2, card.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o cartao " + card.getSerial() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public Card findById(int id) {
        Card card = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cards WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                card = new Card(rs.getInt("serial"));
                card.setId(rs.getInt("id"));
                card.setStatus(rs.getInt("status"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return card;
    }

    @Override
    public List<Card> all() {
        ArrayList<Card> cardList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cards");
            rs = stm.executeQuery();
            while (rs.next()) {
                Card card = new Card(rs.getInt("serial"));
                card.setId(rs.getInt("id"));
                card.setStatus(rs.getInt("status"));
                cardList.add(card);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os cartoes: " + e.getMessage());
        } finally {
            close();
        }
        return cardList;
    }

    public List<Card> allNotBusy() {
        ArrayList<Card> cardList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cards WHERE status = ?");
            stm.setInt(1, 1);
            rs = stm.executeQuery();
            while (rs.next()) {
                Card card = new Card(rs.getInt("serial"));
                card.setId(rs.getInt("id"));
                card.setStatus(rs.getInt("status"));
                cardList.add(card);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os cartoes: " + e.getMessage());
        } finally {
            close();
        }
        return cardList;
    }

    @Override
    public List<Card> findBySerial(String serial) {
        ArrayList<Card> cardList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cards WHERE serial LIKE ? AND status = ?");
            stm.setString(1, "%"+serial+"%");
            stm.setInt(2,1);
            rs = stm.executeQuery();
            while (rs.next()) {
                Card card = new Card(rs.getInt("serial"));
                card.setId(rs.getInt("id"));
                card.setStatus(rs.getInt("status"));
                cardList.add(card);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os cartoes: " + e.getMessage());
        } finally {
            close();
        }
        return cardList;
    }
}

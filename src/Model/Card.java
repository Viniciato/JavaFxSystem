package Model;

import Model.MySql.CardMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public class Card {
    private int id;
    private int serial;
    private int status;
    private static CardDAO dao = new CardMySqlDAO();

    public Card(int serial){
        this.serial = serial;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSerial() {
        return serial;
    }
    public void setSerial(int serial) {
        this.serial = serial;
    }

    public void create(){
        dao.create(this);
    }
    public void update() {
        dao.update(this);
    }

    public static List<Card> all(){
        return dao.all();
    }

    public static List<Card> allNotBusy(){
        return dao.allNotBusy();
    }

    public static List<Card> findByName(String serial){
        return dao.findBySerial(serial);
    }

    public static Card findById(int id){
        return dao.findById(id);
    }

    @Override
    public String toString() {
        return String.valueOf(serial);
    }
}

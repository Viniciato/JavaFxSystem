package Model;

import Model.MySql.TableMySqlDAO;
import Model.TableStatus;

import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public class Table {
    private int id;
    private TableStatus status;
    private int places;
    private String name;
    public static TableDAO dao = new TableMySqlDAO();

    public Table(TableStatus status, int places, String name) {
        this.status = status;
        this.places = places;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return name;
    }


    public void save(){
        if (Table.findById(this.id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public static List<Table> all(){
        return dao.all();
    }
    public static List<Table> allNotBusy(){
        return dao.allNotBusy();
    }
    public static Table findById(int id){
        return dao.findById(id);
    }
    public static List<Table> findByName(String name){
        return dao.findByName(name);
    }

    public void delete(){
        dao.delete(this);
    }
}

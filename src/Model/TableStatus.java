package Model;

import Model.MySql.TableStatusMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public class TableStatus {
    private int id;
    private String name;
    private String description;
    private static TableStatusDAO dao = new TableStatusMySqlDAO();

    public TableStatus(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<TableStatus> all(){
        return dao.all();
    }
    public static TableStatus findById(int id){
        return dao.findById(id);
    }

    @Override
    public String toString() {
        return name;
    }
}

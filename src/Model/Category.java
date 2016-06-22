package Model;

import Model.MySql.CategoryMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 06/11/15.
 */
public class Category {
    private int id;
    private String name;
    private String description;
    private static CategoryDAO dao = new CategoryMySqlDAO();

    public Category(String name) {
        this.name = name;
    }

    // --------------------------------------------------- \\

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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

    // --------------------------------------------------- \\

    public void save(){
        if (dao.findById(id)!=null)
            dao.update(this);
        else
            dao.create(this);
    }

    public static List<Category> all(){
        return dao.all();
    }
    public static List<Category> findByName(String name){
        return dao.findByName(name);
    }
    public static Category findById(int id){
        return dao.findById(id);
    }

    public void delete(){
        dao.delete(this);
    }

    @Override
    public String toString() {
        return name;
    }
}

package Model;

import Model.MySql.StateMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 05/11/15.
 */
public class State {
    private int id;
    private String name;
    private String acronym;
    public static StateDAO dao = new StateMySqlDAO();

    public State(String name, String acronym) {
        this.name = name;
        this.acronym = acronym;
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public static State findById(int id){
        return dao.findById(id);
    }

    public void save(){
        if (State.findById(this.id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public static List<State> all(){
        return dao.all();
    }

    public static List<State> findByName(String name){
        return dao.findByName(name);
    }

    public void delete(){
        dao.delete(this);
    }

    @Override
    public String toString() {
        return name;
    }
}

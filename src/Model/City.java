package Model;

import Model.MySql.CityMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 05/11/15.
 */
public class City {
    private int id;
    private String name;
    private State state;
    public static CityDAO dao = new CityMySqlDAO();

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
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


    public void save(){
        if (City.findById(this.id) != null)
            dao.update(this);
        else
            dao.create(this);
    }
    public void delete(){
        dao.delete(this);
    }
    public static List<City> all(){
        return dao.all();
    }
    public static List<City> findByName(String name){
        return dao.findByName(name);
    }

    public static List<City> findByState(State state){
        return dao.findByState(state);
    }

    public static City findById(int id){
        return dao.findById(id);
    }
    @Override
    public String toString() {
        return this.name;
    }
}

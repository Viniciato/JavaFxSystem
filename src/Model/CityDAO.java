package Model;

import java.util.List;

/**
 * Created by Nadin on 11/11/15.
 */
public interface CityDAO {
    void create(City city);
    void delete(City city);
    void update(City city);
    List<City> all();
    List<City> findByName(String name);
    List<City> findByState(State state);
    City findById(int id);
}

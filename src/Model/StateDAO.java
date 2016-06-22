package Model;

import Model.State;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public interface StateDAO {
    void create(State state);
    void delete(State state);
    void update(State state);
    List<State> all();
    List<State> findByName(String name);
    State findById(int id);
}

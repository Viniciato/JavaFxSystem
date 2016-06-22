package Model;

import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public interface TableDAO {
    void create(Table table);
    void delete(Table table);
    void update(Table table);
    List<Table> all();
    List<Table> allNotBusy();
    List<Table> findByName(String name);
    Table findById(int id);
}

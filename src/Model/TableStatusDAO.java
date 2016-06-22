package Model;

import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public interface TableStatusDAO {
    void create(TableStatus tableStatus);
    void delete(TableStatus tableStatus);
    void update(TableStatus tableStatus);
    List<TableStatus> all();
    TableStatus findById(int id);
}

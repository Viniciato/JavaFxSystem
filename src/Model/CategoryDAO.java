package Model;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public interface CategoryDAO {
    void create(Category category);
    void delete(Category category);
    void update(Category category);
    List<Category> all();
    List<Category> findByName(String name);
    Category findById(int id);
}

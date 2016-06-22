package Model;

import Model.Category;
import Model.Product;

import java.util.List;

/**
 * Created by Nadin on 12/11/15.
 */
public interface ProductDAO {
    void create(Product u);
    void delete(Product u);
    void update(Product u);
    List<Product> all();
    List<Product> allWithCategory(Category category);
    List<Product> findByName(String name);
    Product findById(int id);
}

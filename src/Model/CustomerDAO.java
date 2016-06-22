package Model;

import java.util.List;

/**
 * Created by Nadin on 11/11/15.
 */
public interface CustomerDAO {
    void create(Customer u);
    void delete(Customer u);
    void update(Customer u);
    List<Customer> all();
    List<Customer> findByName(String name);
    Customer findById(int id);
}

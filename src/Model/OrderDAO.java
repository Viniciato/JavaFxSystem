package Model;

import java.util.List;

/**
 * Created by Nadin on 11/11/15.
 */
public interface OrderDAO {
    void create(Order order);
    void delete(Order order);
    void update(Order order);
    List<Order> all();
    List<Order> allWithStatus(OrderStatus status);
    List<Order> allWithCustomer(Customer customer);
    List<Order> allOpen();
    Order findById(int id);
    Order getLastInsertId();
}

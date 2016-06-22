package Model;

import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public interface OrderTableDAO {
    void create(OrderTable orderTable);
    void delete(OrderTable orderTable);
    void update(OrderTable orderTable);
    List<OrderTable> all();
    List<OrderTable> findByOrder(Order order);
    OrderTable findById(int id);
}

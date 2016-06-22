package Model;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public interface OrderItemDAO {
    void create(OrderItem orderItens);
    void delete(OrderItem orderItens);
    void update(OrderItem orderItens);
    List<OrderItem> all();
    List<OrderItem> orderItensByOrder(Order order);
    OrderItem findById(int id);
}

package Model;

import Model.OrderStatus;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public interface OrderStatusDAO {
    void create(OrderStatus orderStatus);
    void delete(OrderStatus orderStatus);
    void update(OrderStatus orderStatus);
    List<OrderStatus> all();
    OrderStatus findById(int id);
}

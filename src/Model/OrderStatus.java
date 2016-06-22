package Model;

import Model.MySql.OrderStatusMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public class OrderStatus {
    private int id;
    private String name;
    private String description;
    private static OrderStatusDAO dao = new OrderStatusMySqlDAO();

    public OrderStatus(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<OrderStatus> all(){
        return dao.all();
    }
    public static OrderStatus findById(int id){
        return dao.findById(id);
    }

    @Override
    public String toString() {
        return name;
    }
}

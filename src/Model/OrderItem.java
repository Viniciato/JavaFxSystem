package Model;

import Model.MySql.OrderItensMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public class OrderItem {
    private int id;
    private Order order;
    private Product product;
    private int amount;
    private Double total;
    private Double price;

    public static OrderItemDAO dao = new OrderItensMySqlDAO();

    public OrderItem(Order order, Product product, Double total, int amount, Double price){
        this.order = order;
        this.total = total;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void save(){
        if (OrderItem.findById(this.id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public void delete(){
        dao.delete(this);
    }

    public static List<OrderItem> all (){
        return dao.all();
    }

    public static List<OrderItem> allByOrder(Order order){
        return dao.orderItensByOrder(order);
    }

    public static OrderItem findById(int id){
        return dao.findById(id);
    }

}

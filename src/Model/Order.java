package Model;

import Model.MySql.OrderMySqlDAO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Nadin on 06/11/15.
 */
public class Order {
    private int id;
    private Customer customer;
    private OrderStatus status;
    private User user;
    private Card card;
    private Double total;
    public static OrderDAO dao = new OrderMySqlDAO();
    private List<OrderTable> tables;
    private Timestamp createdAt;
    private Timestamp createdAtOrganized;

    public Order(Card card, User user) {
        this.card = card;
        this.user = user;
    }

    public String getTableName(){
        String name = "";
        int count =0;
        for (OrderTable orderTable: tables) {
            name = name + orderTable.getTableName();
            count++;
            if (count < tables.size())
                name = name + ", ";
        }
        return name;
    }


    public List<OrderTable> getTables() {
        return tables;
    }

    public void setTables(List<OrderTable> tables) {
        this.tables = tables;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public static List<Order> all(){
        return dao.all();
    }
    public static List<Order> allOpen(){
        return dao.allOpen();
    }
    public static List<Order> allWithStatus(OrderStatus status){
        return dao.allWithStatus(status);
    }

    public static List<Order> allWithCustomer(Customer customer){
        return dao.allWithCustomer(customer);
    }


    public void create(){
        dao.create(this);
    }
    public void update() {
        dao.update(this);
    }
    public static Order getLastInsertId(){
        return dao.getLastInsertId();
    }

    public static Order findById(int id){
        return dao.findById(id);
    }
}

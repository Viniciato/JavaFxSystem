package Model;

import Model.MySql.OrderTableMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public class OrderTable {
    private int id;
    private Order order;
    private Table table;
    public static OrderTableDAO dao = new OrderTableMySqlDAO();

    public OrderTable(Order order, Table table) {
        this.order = order;
        this.table = table;
    }

    public String getTableName(){
        return table.getName();
    }

    public int getTablePlaces(){
        return table.getPlaces();
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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }


    public void create(){
        dao.create(this);
    }
    public void update() {
        dao.update(this);
    }
    public void delete(){
        dao.delete(this);
    }

    public static List<OrderTable> all (){
        return dao.all();
    }

    public static OrderTable findById(int id){
        return dao.findById(id);
    }

    public static List<OrderTable> findByOrder(Order order){
        return dao.findByOrder(order);
    }

    @Override
    public String toString() {
        return String.valueOf(table.getId());
    }
}

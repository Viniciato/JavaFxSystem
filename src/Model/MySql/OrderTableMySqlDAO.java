package Model.MySql;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public class OrderTableMySqlDAO extends MysqlDatabase implements OrderTableDAO {
    @Override
    public void create(OrderTable orderTable) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO order_tables (order_id, table_id) VALUES (?, ?)");
            stm.setInt(1, orderTable.getOrder().getId());
            stm.setInt(2, orderTable.getTable().getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao colocar a mesa no pedido!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(OrderTable orderTable) {
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("DELETE FROM order_tables WHERE id = ?");
            stm.setInt(1, orderTable.getId());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    @Override
    public void update(OrderTable orderTable) {
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("UPDATE order_tables SET order_id = ?, table_id = ? WHERE id = ?");
            stm.setInt(1,orderTable.getOrder().getId());
            stm.setInt(2, orderTable.getTable().getId());
            stm.setInt(3, orderTable.getId());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    @Override
    public List<OrderTable> all() {
        ArrayList<OrderTable> orderTableList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_tables");
            rs = stm.executeQuery();
            while (rs.next()) {
                Order order = Order.findById(rs.getInt("order_id"));
                Table table = Table.findById(rs.getInt("table_id"));
                OrderTable orderTable = new OrderTable(order, table);
                orderTable.setId(rs.getInt("id"));
                orderTableList.add(orderTable);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as mesas da ordem:" + e.getMessage());
        } finally {
            close();
        }
        return orderTableList;
    }

    @Override
    public OrderTable findById(int id) {
        OrderTable orderTable = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_table WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Order order = Order.findById(rs.getInt("order_id"));
                Table table = Table.findById(rs.getInt("table_id"));
                orderTable = new OrderTable(order, table);
                orderTable.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return orderTable;
    }

    @Override
    public List<OrderTable> findByOrder(Order order) {
        ArrayList<OrderTable> tableList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_tables WHERE order_id = ?");
            stm.setInt(1, order.getId());
            rs = stm.executeQuery();
            while (rs.next()) {
                OrderTable orderTable = new OrderTable(order, Table.findById(rs.getInt("table_id")));
                orderTable.setId(rs.getInt("id"));
                tableList.add(orderTable);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as mesas da ordem:" + e.getMessage());
        } finally {
            close();
        }
        return tableList;
    }
}

package Model.MySql;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public class OrderItensMySqlDAO extends MysqlDatabase implements OrderItemDAO {
    @Override
    public void create(OrderItem orderItens) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO order_itens (order_id, product_id, total, amount, price) VALUES (?, ?, ?, ?, ?)");
            stm.setInt(1, orderItens.getOrder().getId());
            stm.setInt(2, orderItens.getProduct().getId());
            stm.setDouble(3, orderItens.getTotal());
            stm.setInt(4, orderItens.getAmount());
            stm.setDouble(5, orderItens.getPrice());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao colocar os itens na ordem!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(OrderItem orderItens) {
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("DELETE FROM order_itens WHERE id=?");
            stm.setInt(1, orderItens.getId());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    @Override
    public void update(OrderItem orderItens) {
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("UPDATE order_itens SET product_id = ?, total = ?, amount = ?, price = ? WHERE id = ?");
            stm.setInt(1, orderItens.getProduct().getId());
            stm.setDouble(2, orderItens.getTotal());
            stm.setInt(3, orderItens.getAmount());
            stm.setDouble(4, orderItens.getPrice());
            stm.setInt(5, orderItens.getId());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    @Override
    public List<OrderItem> all() {
        ArrayList<OrderItem> orderItensList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_itens");
            rs = stm.executeQuery();
            while (rs.next()) {
                Order order = Order.findById(rs.getInt("order_id"));
                Product product = Product.findById(rs.getInt("product_id"));
                Double total = rs.getDouble("total");
                int amount = rs.getInt("amount");
                Double price = rs.getDouble("price");
                OrderItem orderItens = new OrderItem(order, product, total, amount, price);
                orderItens.setId(rs.getInt("id"));
                orderItensList.add(orderItens);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os itens da ordem:" + e.getMessage());
        } finally {
            close();
        }
        return orderItensList;
    }

    @Override
    public List<OrderItem> orderItensByOrder(Order order) {
        ArrayList<OrderItem> orderItensList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_itens WHERE order_id = ?");
            stm.setInt(1, order.getId());
            rs = stm.executeQuery();
            while (rs.next()) {
                Order newOrder = Order.findById(rs.getInt("order_id"));
                Product product = Product.findById(rs.getInt("product_id"));
                Double total = rs.getDouble("total");
                int amount = rs.getInt("amount");
                Double price = rs.getDouble("price");
                OrderItem orderItens = new OrderItem(newOrder, product, total, amount, price);
                orderItens.setId(rs.getInt("id"));
                orderItensList.add(orderItens);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os itens da ordem:" + e.getMessage());
        } finally {
            close();
        }
        return orderItensList;
    }

    @Override
    public OrderItem findById(int id) {
        OrderItem orderItens = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_itens WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Order newOrder = Order.findById(rs.getInt("order_id"));
                Product product = Product.findById(rs.getInt("product_id"));
                Double total = rs.getDouble("total");
                int amount = rs.getInt("amount");
                Double price = rs.getDouble("price");
                orderItens = new OrderItem(newOrder, product, total, amount, price);
                orderItens.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return orderItens;
    }
}

package Model.MySql;

import Model.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Nadin on 11/11/15.
 */
public class OrderMySqlDAO extends MysqlDatabase implements OrderDAO {
    @Override
    public void create(Order order) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO orders (customer_id, user_id, order_status_id, card_id, created_at, total) VALUES (?, ?, ?, ?, ?, ?)");
            if (order.getCustomer() == null)
                stm.setNull(1, Types.INTEGER);
            else
                stm.setInt(1, order.getCustomer().getId());

            if (Integer.valueOf(order.getUser().getId()) != null)
                stm.setInt(2, order.getUser().getId());
            else
                stm.setNull(2, Types.INTEGER);
            stm.setInt(3, order.getStatus().getId());
            stm.setInt(4, order.getCard().getId());
            java.util.Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            stm.setTimestamp(5, timestamp);
            stm.setDouble(6, order.getTotal());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar a ordem!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(Order u) {

    }

    @Override
    public void update(Order order) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("UPDATE orders SET customer_id = ?, order_status_id = ?, total = ? WHERE id = ?");
            if (order.getCustomer() == null)
                stm.setNull(1, Types.INTEGER);
            else
                stm.setInt(1, order.getCustomer().getId());
            stm.setInt(2, order.getStatus().getId());
            stm.setDouble(3, order.getTotal());
            stm.setInt(4, order.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a ordem!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<Order> all() {
        ArrayList<Order> ordersList = new ArrayList<>();
       open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM orders WHERE order_status_id <> ?");
            stm.setInt(1,1);
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = User.findById(rs.getInt("user_id"));
                Customer customer = Customer.findById(rs.getInt("customer_id"));
                Card card = Card.findById(rs.getInt("card_id"));
                Order order = new Order(card, user);
                order.setCustomer(customer);
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(OrderStatus.findById(rs.getInt("order_status_id")));
                List<OrderTable> tables = OrderTable.findByOrder(order);
                order.setTables(tables);
                order.setCreatedAt(rs.getTimestamp("created_at"));
                ordersList.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as ordens:" + e.getMessage());
        } finally {
            close();
        }
        return ordersList;
    }

    @Override
    public List<Order> allOpen() {
        ArrayList<Order> ordersList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM orders WHERE order_status_id = ? ORDER BY created_at DESC");
            stm.setInt(1, 1);
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = User.findById(rs.getInt("user_id"));
                Customer customer = Customer.findById(rs.getInt("customer_id"));
                Card card = Card.findById(rs.getInt("card_id"));
                Order order = new Order(card, user);
                order.setCustomer(customer);
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(OrderStatus.findById(rs.getInt("order_status_id")));
                List<OrderTable> tables = OrderTable.findByOrder(order);
                order.setTables(tables);
                order.setCreatedAt(rs.getTimestamp("created_at"));
                ordersList.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as ordens:" + e.getMessage());
        } finally {
            close();
        }
        return ordersList;
    }

    @Override
    public List<Order> allWithStatus(OrderStatus status) {
        ArrayList<Order> ordersList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM orders WHERE order_status_id = ?");
            stm.setInt(1, status.getId());
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = User.findById(rs.getInt("user_id"));
                Customer customer = Customer.findById(rs.getInt("customer_id"));
                Card card = Card.findById(rs.getInt("card_id"));
                Order order = new Order(card, user);
                order.setCustomer(customer);
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(OrderStatus.findById(rs.getInt("order_status_id")));
                List<OrderTable> tables = OrderTable.findByOrder(order);
                order.setTables(tables);
                ordersList.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as ordens:" + e.getMessage());
        } finally {
            close();
        }
        return ordersList;
    }

    @Override
    public List<Order> allWithCustomer(Customer customer) {
        ArrayList<Order> ordersList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM orders WHERE customer_id = ? AND order_status_id <> 1 ORDER BY id");
            stm.setInt(1, customer.getId());
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = User.findById(rs.getInt("user_id"));
                Card card = Card.findById(rs.getInt("card_id"));
                Order order = new Order(card, user);
                order.setCustomer(customer);
                order.setId(rs.getInt("id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(OrderStatus.findById(rs.getInt("order_status_id")));
                List<OrderTable> tables = OrderTable.findByOrder(order);
                order.setTables(tables);
                ordersList.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as ordens:" + e.getMessage());
        } finally {
            close();
        }
        return ordersList;
    }

    @Override
    public Order getLastInsertId() {
        Order order = null;
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("select last_insert_id() as id from orders");
            rs = stm.executeQuery();
            order = Order.findById(rs.getInt("id"));
        } catch (SQLException e) {
            System.err.println("Erro ao listar as ordens:" + e.getMessage());
        } finally {
            close();
        }
        return order;
    }

    @Override
    public Order findById(int id) {
        return null;
    }
}

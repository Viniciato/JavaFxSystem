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
public class OrderStatusMySqlDAO extends MysqlDatabase implements OrderStatusDAO {

    @Override
    public void create(OrderStatus orderStatus) {

    }

    @Override
    public void delete(OrderStatus orderStatus) {

    }

    @Override
    public void update(OrderStatus orderStatus) {

    }

    @Override
    public List<OrderStatus> all() {
        ArrayList<OrderStatus> orderStatusList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_status");
            rs = stm.executeQuery();
            while (rs.next()) {
                OrderStatus orderStatus = new OrderStatus(rs.getString("name"));
                orderStatus.setId(rs.getInt("id"));
                orderStatus.setDescription(rs.getString("description"));
                orderStatusList.add(orderStatus);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar o OrderStatus:" + e.getMessage());
        } finally {
            close();
        }
        return orderStatusList;
    }

    @Override
    public OrderStatus findById(int id) {
        OrderStatus orderStatus = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM order_status WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                orderStatus = new OrderStatus(rs.getString("name"));
                orderStatus.setId(rs.getInt("id"));
                orderStatus.setDescription(rs.getString("description"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return orderStatus;
    }
}

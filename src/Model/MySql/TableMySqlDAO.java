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
public class TableMySqlDAO extends MysqlDatabase implements TableDAO {
    @Override
    public void create(Table table) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO tables (name, places, table_status_id) VALUES (?, ?, ?)");
            stm.setString(1, table.getName());
            stm.setInt(2, table.getPlaces());
            stm.setInt(3, table.getStatus().getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar a mesa!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(Table table) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM tables WHERE id = ?");
            stm.setInt(1, table.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar a mesa " + table.getName() + ": " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(Table table) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("UPDATE tables SET name = ?, places = ?, table_status_id = ? WHERE id = ?");
            stm.setString(1, table.getName());
            stm.setInt(2, table.getPlaces());
            stm.setInt(3, table.getStatus().getId());
            stm.setInt(4, table.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a mesa " + table.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<Table> all() {
        ArrayList<Table> tablesList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM tables");
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                TableStatus status = TableStatus.findById(rs.getInt("table_status_id"));
                int places = rs.getInt("places");
                String name = rs.getString("name");
                Table table = new Table(status, places, name);
                table.setId(id);
                tablesList.add(table);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as mesas: " + e.getMessage());
        } finally {
            close();
        }
        return tablesList;
    }

    @Override
    public List<Table> allNotBusy() {
        ArrayList<Table> tablesList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM tables WHERE table_status_id = ?");
            stm.setInt(1,1);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                TableStatus status = TableStatus.findById(rs.getInt("table_status_id"));
                int places = rs.getInt("places");
                String name = rs.getString("name");
                Table table = new Table(status, places, name);
                table.setId(id);
                tablesList.add(table);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as mesas: " + e.getMessage());
        } finally {
            close();
        }
        return tablesList;
    }

    @Override
    public Table findById(int id) {
        Table table = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM tables WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                int id_table = rs.getInt("id");
                TableStatus status = TableStatus.findById(rs.getInt("table_status_id"));
                int places = rs.getInt("places");
                String name = rs.getString("name");
                table = new Table(status, places, name);
                table.setId(id_table);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return table;
    }

    @Override
    public List<Table> findByName(String name) {
        ArrayList<Table> tableList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM tables WHERE name LIKE ?");
            stm.setString(1, "%"+name+"%");
            rs = stm.executeQuery();
            while (rs.next()) {
                TableStatus status = TableStatus.findById(rs.getInt("table_status_id"));
                Table table = new Table(status, rs.getInt("places"), rs.getString("name"));
                table.setId(rs.getInt("id"));
                tableList.add(table);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as mesas: " + e.getMessage());
        } finally {
            close();
        }
        return tableList;
    }
}

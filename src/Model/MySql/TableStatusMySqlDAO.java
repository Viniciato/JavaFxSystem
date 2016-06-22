package Model.MySql;

import Model.TableStatus;
import Model.TableStatusDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 18/11/15.
 */
public class TableStatusMySqlDAO extends MysqlDatabase implements TableStatusDAO {
    @Override
    public void create(TableStatus tableStatus) {

    }

    @Override
    public void delete(TableStatus tableStatus) {

    }

    @Override
    public void update(TableStatus tableStatus) {

    }

    @Override
    public List<TableStatus> all() {
        ArrayList<TableStatus> tableStatusList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM table_status");
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                TableStatus tableStatus = new TableStatus(name);
                tableStatus.setId(id);
                tableStatus.setDescription(description);
                tableStatusList.add(tableStatus);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as mesas: " + e.getMessage());
        } finally {
            close();
        }
        return tableStatusList;
    }

    @Override
    public TableStatus findById(int id) {
        TableStatus tableStatus = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM table_status WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String description = rs.getString("description");
                tableStatus = new TableStatus(name);
                tableStatus.setDescription(description);
                tableStatus.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return tableStatus;
    }
}

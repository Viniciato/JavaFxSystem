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
public class StateMySqlDAO extends MysqlDatabase implements StateDAO {
    @Override
    public void create(State state) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO states (name, acronym) VALUES (?, ?)");
            stm.setString(1, state.getName());
            stm.setString(2, state.getAcronym());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar o Estado!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(State state) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM states WHERE id = ?");
            stm.setInt(1, state.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o estado " + state.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(State state) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("UPDATE states SET name = ?, acronym = ? WHERE id = ?");
            stm.setString(1, state.getName());
            stm.setString(2, state.getAcronym());
            stm.setInt(3, state.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o estado " + state.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<State> all() {
        ArrayList<State> statesList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM states");
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String acronym = rs.getString("acronym");
                State state = new State(name, acronym);
                state.setId(rs.getInt("id"));
                statesList.add(state);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os estados:" + e.getMessage());
        } finally {
            close();
        }
        return statesList;
    }

    @Override
    public State findById(int id) {
        State state = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM states WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                state = new State(rs.getString("name"), rs.getString("acronym"));
                state.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return state;
    }

    @Override
    public List<State> findByName(String name) {
        ArrayList<State> stateList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM states WHERE name LIKE ?");
            stm.setString(1, "%"+name+"%");
            rs = stm.executeQuery();
            while (rs.next()) {
                State state = new State(rs.getString("name"), rs.getString("acronym"));
                state.setId(rs.getInt("id"));
                stateList.add(state);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os estados: " + e.getMessage());
        } finally {
            close();
        }
        return stateList;
    }
}

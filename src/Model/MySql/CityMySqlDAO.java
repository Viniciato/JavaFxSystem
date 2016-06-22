package Model.MySql;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 11/11/15.
 */
public class CityMySqlDAO extends MysqlDatabase implements CityDAO {

    @Override
    public void create(City city) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO cities (name, state_id) VALUES (?, ?)");
            stm.setString(1, city.getName());
            stm.setInt(2, city.getState().getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar a Cidade!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(City city) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM cities WHERE id = ?");
            stm.setInt(1, city.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o estado " + city.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(City city) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("UPDATE cities SET name = ?, state_id = ? WHERE id = ?");
            stm.setString(1, city.getName());
            stm.setInt(2, city.getState().getId());
            stm.setInt(3, city.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o estado " + city.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<City> all() {
        ArrayList<City> citiesList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cities");
            rs = stm.executeQuery();
            while (rs.next()) {
                State state = State.findById(rs.getInt("state_id"));
                City city = new City(rs.getString("name"), state);
                city.setId(rs.getInt("id"));
                citiesList.add(city);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as cidades:" + e.getMessage());
        } finally {
            close();
        }
        return citiesList;
    }

    @Override
    public City findById(int id) {
        City city = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cities WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                State state = State.findById(rs.getInt("state_id"));
                city = new City(rs.getString("name"), state);
                city.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return city;
    }

    @Override
    public List<City> findByName(String name) {
        ArrayList<City> cityList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cities WHERE name LIKE ?");
            stm.setString(1, "%"+name+"%");
            rs = stm.executeQuery();
            while (rs.next()) {
                State state = State.findById(rs.getInt("state_id"));
                City city = new City(rs.getString("name"), state);
                city.setId(rs.getInt("id"));
                cityList.add(city);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as cidades: " + e.getMessage());
        } finally {
            close();
        }
        return cityList;
    }

    @Override
    public List<City> findByState(State state) {
        ArrayList<City> citiesList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM cities WHERE state_id = ?");
            stm.setInt(1, state.getId());
            rs = stm.executeQuery();
            while (rs.next()) {
                State stateCity = State.findById(rs.getInt("state_id"));
                City city = new City(rs.getString("name"), state);
                city.setId(rs.getInt("id"));
                citiesList.add(city);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as cidades: " + e.getMessage());
        } finally {
            close();
        }
        return citiesList;
    }
}

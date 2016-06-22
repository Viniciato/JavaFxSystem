package Model.MySql;

import Model.User;
import Model.UserDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 09/11/15.
 */
public class UserMySqlDAO extends MysqlDatabase implements UserDAO {
    public UserMySqlDAO() {
    }

    @Override
    public void create(User user) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO users (name, cpf, salary, password, access, created_at) VALUES (?, ?, ?, ?, ?, ?)");
            stm.setString(1, user.getName());
            stm.setString(2, user.getCpf());
            stm.setDouble(3, user.getSalary());
            stm.setString(4, user.getPassword());
            stm.setInt(5, user.getAccess());
            stm.setDate(6, new Date(2015, 11, 13));
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar o Usuario!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(User user) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            stm.setInt(1, user.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o usuario " + user.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(User user){
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("UPDATE users SET name = ?, cpf = ?, salary = ?, " +
                    "password = ?, access = ? WHERE id = ?");
            stm.setString(1, user.getName());
            stm.setString(2, user.getCpf());
            stm.setDouble(3, user.getSalary());
            stm.setString(4, user.getPassword());
            stm.setInt(5, user.getAccess());
            stm.setInt(6, user.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o usuario " + user.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<User> all() {
        ArrayList<User> usersList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM users ORDER BY access DESC");
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");
                String cpf = rs.getString("cpf");
                Double salary = rs.getDouble("salary");
                int access = rs.getInt("access");
                User user = new User(name, password, cpf, salary, access);
//                user.setCreated_at(rs.getDate("created_at"));
                user.setId(rs.getInt("id"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os usuarios:" + e.getMessage());
        } finally {
            close();
        }
        return usersList;
    }

    @Override
    public User login(String name, String password) {
        User user = null;
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM users WHERE name = ? AND password = ?");
            stm.setString(1, name);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                String nameLoaded = rs.getString("name");
                String passwordLoaded = rs.getString("password");
                String cpf = rs.getString("cpf");
                Double salary = rs.getDouble("salary");
                int access = rs.getInt("access");
                user = new User(nameLoaded, passwordLoaded, cpf, salary, access);
                user.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Usuario Invalido!! " + e.getMessage());
        } finally {
            close();
        }
        return user;
    }

    @Override
    public boolean verifyCpf(String cpf) {
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM users WHERE cpf = ?");
            stm.setString(1, cpf);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findById(int id) {
        User user = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String password = rs.getString("password");
                String cpf = rs.getString("cpf");
                Double salary = rs.getDouble("salary");
                int access = rs.getInt("access");
                user = new User(name, password, cpf, salary, access);
                user.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return user;
    }

    @Override
    public List<User> findByName(String name) {
        ArrayList<User> usersList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM users WHERE name LIKE ?");
            stm.setString(1, "%"+name+"%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String usrName = rs.getString("name");
                String password = rs.getString("password");
                String cpf = rs.getString("cpf");
                Double salary = rs.getDouble("salary");
                int access = rs.getInt("access");
                User user = new User(usrName, password, cpf, salary, access);
//                user.setCreated_at(rs.getDate("created_at"));
                user.setId(rs.getInt("id"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os usuarios:" + e.getMessage());
        } finally {
            close();
        }
        return usersList;
    }

}

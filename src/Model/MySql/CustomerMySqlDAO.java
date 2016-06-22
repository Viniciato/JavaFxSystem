package Model.MySql;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 11/11/15.
 */
public class CustomerMySqlDAO extends MysqlDatabase implements CustomerDAO {

    @Override
    public void create(Customer customer) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO customers (name, cpf, phone_number, email, city_id) VALUES (?, ?, ?, ?, ?)");
            stm.setString(1, customer.getName());
            stm.setLong(3, customer.getPhoneNumber());
            stm.setInt(5, customer.getCity().getId());
            if (Long.valueOf(customer.getCpf()) != 0)
                stm.setLong(2, customer.getCpf());
            else
                stm.setNull(2, Types.VARCHAR);
            if (customer.getEmail()!=null)
                stm.setString(4, customer.getEmail());
            else
                stm.setNull(4, Types.VARCHAR);

            stm.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062)
                throw new InvalidCpfException("CPF Deve ser Unico!!");
            else
                throw new RuntimeException(e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(Customer customer) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM customers WHERE id = ?");
            stm.setInt(1, customer.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o usuario " + customer.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(Customer customer){
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("UPDATE customers SET name = ?, cpf = ?, phone_number = ?, email = ?, city_id = ?, " +
                    "street = ?, address_number = ?, neighborhood = ?, zip_code = ?, comments = ?  WHERE id = ?");
            stm.setString(1, customer.getName());
            stm.setLong(3, customer.getPhoneNumber());
            stm.setInt(5, customer.getCity().getId());
            if (Long.valueOf(customer.getCpf()) != 0)
                stm.setLong(2, customer.getCpf());
            else
                stm.setNull(2, Types.VARCHAR);
            if (customer.getEmail()!=null)
                stm.setString(4, customer.getEmail());
            else
                stm.setNull(4, Types.VARCHAR);
            stm.setString(6, customer.getStreet());
            stm.setInt(7, customer.getAddressNumber());
            stm.setString(8, customer.getNeighborhood());
            stm.setInt(9, customer.getZipCode());
            stm.setString(10, customer.getComments());
            stm.setInt(11, customer.getId());

            stm.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062)
                throw new InvalidCpfException("CPF Deve ser Unico!!");
            else
                throw new RuntimeException(e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public List<Customer> all() {
        ArrayList<Customer> customersList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM customers");
            rs = stm.executeQuery();
            while (rs.next()) {
                City city = City.findById(rs.getInt("city_id"));
                Customer customer = new Customer(rs.getString("name"), city);
                customer.setId(rs.getInt("id"));
                customer.setCpf(rs.getLong("cpf"));
                customer.setPhoneNumber(rs.getLong("phone_number"));
                customer.setEmail(rs.getString("email"));
                customer.setStreet(rs.getString("street"));
                customer.setAddressNumber(rs.getInt("address_number"));
                customer.setNeighborhood(rs.getString("neighborhood"));
                customer.setZipCode(rs.getInt("zip_code"));
                customer.setComments(rs.getString("comments"));
                customersList.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os clientes:" + e.getMessage());
        } finally {
            close();
        }
        return customersList;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                City city = City.findById(rs.getInt("city_id"));
                customer = new Customer(rs.getString("name"), city);
                customer.setId(rs.getInt("id"));
                customer.setEmail(rs.getString("email"));
                customer.setCpf(rs.getLong("cpf"));
                customer.setPhoneNumber(rs.getLong("phone_number"));
                customer.setStreet(rs.getString("street"));
                customer.setAddressNumber(rs.getInt("address_number"));
                customer.setNeighborhood(rs.getString("neighborhood"));
                customer.setZipCode(rs.getInt("zip_code"));
                customer.setComments(rs.getString("comments"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return customer;
    }

    @Override
    public List<Customer> findByName(String name) {
        ArrayList<Customer> customerList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM customers WHERE name LIKE ?");
            stm.setString(1, "%"+name+"%");
            rs = stm.executeQuery();
            while (rs.next()) {
                City city = City.findById(rs.getInt("city_id"));
                Customer customer = new Customer(rs.getString("name"), city);
                customer.setId(rs.getInt("id"));
                customer.setEmail(rs.getString("email"));
                customer.setCpf(rs.getLong("cpf"));
                customer.setPhoneNumber(rs.getLong("phone_number"));
                customer.setStreet(rs.getString("street"));
                customer.setAddressNumber(rs.getInt("address_number"));
                customer.setNeighborhood(rs.getString("neighborhood"));
                customer.setZipCode(rs.getInt("zip_code"));
                customer.setComments(rs.getString("comments"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as clientes: " + e.getMessage());
        } finally {
            close();
        }
        return customerList;
    }
}

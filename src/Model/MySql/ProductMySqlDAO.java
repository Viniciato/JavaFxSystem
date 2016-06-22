package Model.MySql;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 12/11/15.
 */
public class ProductMySqlDAO extends MysqlDatabase implements ProductDAO {

    @Override
    public void create(Product product) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO products (name, category_id, price, description) VALUES (?, ?, ?, ?)");
            stm.setString(1, product.getName());
            stm.setInt(2, product.getCategory().getId());
            stm.setDouble(3, product.getPrice());
            stm.setString(4, product.getDescription());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar o produto!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(Product product) {
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("DELETE FROM products WHERE id=?");
            stm.setInt(1, product.getId());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    @Override
    public void update(Product product){
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("UPDATE products SET name = ?, category_id = ?, price = ?, description = ? WHERE id = ?");
            stm.setString(1, product.getName());
            stm.setInt(2, product.getCategory().getId());
            stm.setDouble(3, product.getPrice());
            if (product.getDescription() == null)
                stm.setNull(4, Types.VARCHAR);
            else
                stm.setString(4, product.getDescription());
            stm.setInt(5, product.getId());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    @Override
    public List<Product> allWithCategory(Category category) {
        ArrayList<Product> productList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM products WHERE category_id = ?");
            stm.setInt(1, category.getId());
            rs = stm.executeQuery();
            while (rs.next()) {
                Category loadedCategory = Category.findById(rs.getInt("category_id"));
                Product product = new Product(rs.getString("name"), rs.getDouble("price"), loadedCategory);
                product.setDescription(rs.getString("description"));
                product.setId(rs.getInt("id"));
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os produtos:" + e.getMessage());
        } finally {
            close();
        }
        return productList;
    }

    @Override
    public List<Product> all() {
        ArrayList<Product> productList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM products");
            rs = stm.executeQuery();
            while (rs.next()) {
                Category category = Category.findById(rs.getInt("category_id"));
                Product product = new Product(rs.getString("name"), rs.getDouble("price"), category);
                product.setDescription(rs.getString("description"));
                product.setId(rs.getInt("id"));
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os produtos:" + e.getMessage());
        } finally {
            close();
        }
        return productList;
    }

    @Override
    public List<Product> findByName(String name) {
        ArrayList<Product> productList = new ArrayList<>();
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM products WHERE name LIKE ?");
            stm.setString(1, "%"+name+"%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Category category = Category.findById(rs.getInt("category_id"));
                Product product = new Product(rs.getString("name"), rs.getDouble("price"), category);
                product.setDescription(rs.getString("description"));
                product.setId(rs.getInt("id"));
                productList.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return productList;
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM products WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Category category = Category.findById(rs.getInt("category_id"));
                product = new Product(rs.getString("name"), rs.getDouble("price"), category);
                product.setDescription(rs.getString("description"));
                product.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return product;
    }
}

package Model.MySql;

import Model.Category;
import Model.CategoryDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public class CategoryMySqlDAO extends MysqlDatabase implements CategoryDAO {

    @Override
    public List<Category> all() {
        ArrayList<Category> categoryList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM categories");
            rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getString("name"));
                category.setId(rs.getInt("id"));
                category.setDescription(rs.getString("description"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as categorias: " + e.getMessage());
        } finally {
            close();
        }
        return categoryList;
    }

    @Override
    public List<Category> findByName(String name) {
        ArrayList<Category> categoryList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM categories WHERE name LIKE ?");
            stm.setString(1, "%"+name+"%");
            rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getString("name"));
                category.setId(rs.getInt("id"));
                category.setDescription(rs.getString("description"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as categorias: " + e.getMessage());
        } finally {
            close();
        }
        return categoryList;
    }

    @Override
    public Category findById(int id) {
        Category category = null;
        open();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM categories WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                category = new Category(rs.getString("name"));
                category.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return category;
    }

    @Override
    public void create(Category category) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO categories (name, description) VALUES (?, ?)");
            stm.setString(1, category.getName());
            stm.setString(2, category.getDescription());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao criar a Categoria!! " + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void delete(Category category) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM categories WHERE id = ?");
            stm.setInt(1, category.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar a categoria " + category.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(Category category) {
        open();
        try {
            PreparedStatement stm = conn.prepareStatement("UPDATE categories SET name = ?, description = ? WHERE id = ?");
            stm.setString(1, category.getName());
            stm.setString(2, category.getDescription());
            stm.setInt(3, category.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a categoria " + category.getName() + ":" + e.getMessage());
        } finally {
            close();
        }
    }
}

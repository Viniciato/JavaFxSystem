package Model;

import Model.MySql.ProductMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 06/11/15.
 */
public class Product {
    private int id;
    private String name;
    private Category category;
    private Double price;
    private String description;
    private static ProductDAO dao = new ProductMySqlDAO();

    public Product(String name, Double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // --------------------------------------------------- \\

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    // --------------------------------------------------- \\

    public void save(){
        if (Product.findById(this.id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public void delete() { dao.delete(this);  }

    public static List<Product> all(){
        return dao.all();
    }
    public static List<Product> allWithCategory(Category category){
        return dao.allWithCategory(category);
    }
    public static List<Product> findByName(String name){
        return dao.findByName(name);
    }
    public static Product findById(int id){
        return dao.findById(id);

    }

    @Override
    public String toString() {
        return name;
    }
}

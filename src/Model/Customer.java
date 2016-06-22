package Model;

import Model.MySql.CustomerMySqlDAO;

import java.util.List;

/**
 * Created by Nadin on 11/11/15.
 */
public class Customer {
    private int id;
    private String name;
    private long cpf;
    private long phoneNumber;
    private String email;
    private City city;
    private String street;
    private int addressNumber;
    private String neighborhood;
    private int zipCode;
    private String comments;
    private static CustomerDAO dao = new CustomerMySqlDAO();

    public Customer(String name, City city){
        this.name = name;
        this.city = city;
    }

    // --------------------------------------------------- \\


    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public int getAddressNumber() {
        return addressNumber;
    }
    public void setAddressNumber(int addressNumber) {
        this.addressNumber = addressNumber;
    }
    public String getNeighborhood() {
        return neighborhood;
    }
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
    public int getZipCode() {
        return zipCode;
    }
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
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
    public long getCpf() {
        return cpf;
    }
    public void setCpf(long cpf) {
        this.cpf = cpf;
    }
    public long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // --------------------------------------------------- \\

    public void save(){
        if (Customer.findById(this.id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public static List<Customer> all(){
        return dao.all();
    }
    public static List<Customer> findByName(String name){
        return dao.findByName(name);
    }
    public static Customer findById(int id){
        return dao.findById(id);
    }

    @Override
    public String toString() {
        return name;
    }
}

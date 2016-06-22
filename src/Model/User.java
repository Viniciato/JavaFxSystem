package Model;

import Model.MySql.UserMySqlDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by Nadin on 05/11/15.
 */
public class User {
    private int id;
    private String name;
    private String cpf;
    private double salary;
    private String email;
    private String password;
    private Date created_at;
    private int access;
    private String phoneNumber;
    private static UserDAO dao = new UserMySqlDAO();

    public User(String name, String password, String cpf, double salary, int access) {
        this.name = name;
        this.password = password;
        this.cpf = cpf;
        this.salary = salary;
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // -----------------------------

    public static User login(String nome, String password){
        return dao.login(nome, password);
    }

    public void save(){
        if (User.findById(this.id) != null)
            dao.update(this);
        else
            dao.create(this);
    }

    public boolean verifyCpf(String cpf){
        return dao.verifyCpf(cpf);
    }

    public void delete(){
        dao.delete(this);
    }

    public static List<User> all(){
        return dao.all();
    }
    public static User findById(int id){
        return dao.findById(id);
    }
    public static List<User> findByName(String name){
        return dao.findByName(name);
    }
    @Override
    public String toString() {
        return name;
    }
}

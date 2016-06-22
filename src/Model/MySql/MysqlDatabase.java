package Model.MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nadin on 10/11/15.
 */
public class MysqlDatabase {

    private static final String URL = "jdbc:mysql://localhost:3307/";
    private static final String DATABASE = "RestaurantDB";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "120596";

    protected Connection conn;
    protected Statement stm;

    public void open() {
        conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
            stm = conn.createStatement();
        } catch (SQLException e) {
            System.err.println(e);
        }catch (ClassNotFoundException e){
            System.err.println(e);
        }
        System.out.println("Connected");
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com o banco:" + e);
        }
    }
}

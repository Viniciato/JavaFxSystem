package Model;

import java.util.List;

/**
 * Created by Nadin on 09/11/15.
 */
public interface UserDAO {
    void create(User user);
    void delete(User user);
    void update(User user);
    List<User> all();
    User login(String nome, String password);
    User findById(int id);
    List<User> findByName(String name);
    boolean verifyCpf(String cpf);
}

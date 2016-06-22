package Model;

import java.util.List;

/**
 * Created by Nadin on 13/11/15.
 */
public interface CardDAO {
    void create(Card card);
    void delete(Card card);
    void update(Card card);
    Card findById(int id);
    List<Card> all();
    List<Card> allNotBusy();
    List<Card> findBySerial(String serial);

}

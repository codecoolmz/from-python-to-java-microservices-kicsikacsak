package dao;

import model.Product;
import org.json.JSONException;


/**
 * Created by svindler on 19.01.2017.
 */
public interface productDao {
    void add(Product product);
    Product selectMostBoughtItem(String user);
    void updateQuantity(Integer newQuantity, String productName, String user);
    boolean checkClientAPIKEY(String jsonStr) throws JSONException;
}

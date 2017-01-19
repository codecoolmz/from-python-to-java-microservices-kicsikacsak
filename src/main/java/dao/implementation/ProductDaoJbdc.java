package dao.implementation;

import dao.productDao;
import db_connector.DataBaseConnectorImpl;
import model.Product;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by svindler on 19.01.2017.
 */

public class ProductDaoJbdc implements productDao {

    private static DataBaseConnectorImpl connector = DataBaseConnectorImpl.getInstance();

    private static ProductDaoJbdc instance = null;


    private ProductDaoJbdc() {
    }

    public static ProductDaoJbdc getInstance(){
        if (instance == null){
            instance = new ProductDaoJbdc();
        }
        return instance;
    }

    public void add(Product product) {

        String query = "INSERT INTO product (webshop_user, name, category, default_price, quantity) VALUES (?, ?, ?, ?, ?);";
        try (Connection connection = connector.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, product.getUser());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setString(4, product.getPrice());
            preparedStatement.setInt(5, product.getQuantity());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Product selectMostBoughtItem(String user) {
        String query = "SELECT * FROM product WHERE webshop_user = ?  AND quantity = (select max(quantity) from product)";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return  new Product(
                        resultSet.getString("webshop_user"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("default_price"),
                        resultSet.getInt("quantity"));
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void updateQuantity(Integer newQuantity, String productName, String user) {
        String query =  "UPDATE product SET quantity = ? WHERE name = ? AND webshop_user = ?;";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, newQuantity);
            statement.setString(2, productName);
            statement.setString(3, user);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getQuantity(String productName) {
        String query = "SELECT * FROM product WHERE name = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                System.out.println(resultSet.getInt("quantity"));
                return resultSet.getInt("quantity");
            }
            else {
                    return null;
                }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkIfProductIsInDatabase(String productName) {
        String query = "SELECT * FROM product WHERE name = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();


            return (resultSet.next());

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkClientAPIKEY(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        Integer apikey = jsonObject.getInt("apikey");

        String query = "SELECT * FROM client WHERE apikey = ? ;";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))

        {
            statement.setString(1, apikey.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Client APIKey found");
                return true;

            } else {
                System.out.println("No APIKey was found");
                return false;
            }

        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

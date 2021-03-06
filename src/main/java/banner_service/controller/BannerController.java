package banner_service.controller;

import banner_service.Service.Service;
import db_connector.DataBaseConnectorImpl;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by svindler on 10.01.2017.
 */

/**
 * Controller class for the API
 *
 * @author mz
 * @version 1.0
 */

public class BannerController {

    private final Service service;
    private static DataBaseConnectorImpl connector = DataBaseConnectorImpl.getInstance();


    public BannerController(Service service){
        this.service = service;
    }

    /**
     * @param request, standard spark request
     * @param response, standard spark response
     * @return a JSONObject
     */

    public JSONObject getBanner(Request request, Response response){

        System.out.println(request.body());

        JSONObject jsonObject = null;

        if(request.body() != null && !request.body().isEmpty()) {

            jsonObject = new JSONObject(request.body());

            if(jsonObject.length() == 0) {
                return service.getBanner();
            }
            if (checkClientAPIKEY(request.body())) {
                System.out.println(checkClientAPIKEY(request.body()));
                if (!jsonObject.has("cart")) {
                    return service.getBanner(jsonObject.get("user").toString());
                }
                return service.getBanner(jsonObject.get("user").toString(), jsonObject.get("cart").toString());
            }
            else {
                response.status(400);
                jsonObject = new JSONObject("{error:API key required please contact the developers}");
            }

        }else if(request.body().isEmpty()) {
            response.status(400);
            jsonObject = new JSONObject("{error:Empty request body}");
        }
        return jsonObject;

    }

    /** This method is checking that the database contains the apikey from the request
     *
     * @param jsonStr, string extracted from a JSONobject
     * @return true if the database contains the apikey, false if it doesn't
     * @throws JSONException indicates that some exception happened during JSON processing
     * @see BannerController
     */

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

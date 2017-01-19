package banner_service.controller;

import banner_service.Service.Service;
import dao.implementation.ProductDaoJbdc;
import db_connector.DataBaseConnectorImpl;
import org.json.JSONArray;
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

public class BannerController {

    private final Service service;
    private ProductDaoJbdc productDaoJbdc = ProductDaoJbdc.getInstance();


    public BannerController(Service service){
        this.service = service;
    }

    public JSONObject getBanner(Request request, Response response){

        System.out.println(request.body());

        JSONObject jsonObject = null;

        if(request.body() != null && !request.body().isEmpty()) {

            jsonObject = new JSONObject(request.body());

            if(jsonObject.length() == 0) {
                return service.getBanner();
            }
            if (productDaoJbdc.checkClientAPIKEY(request.body())) {
                System.out.println(productDaoJbdc.checkClientAPIKEY(request.body()));
                if (!jsonObject.has("cart")) {
                    return service.getBanner(jsonObject.get("user").toString());
                }

                return service.getBanner(jsonObject.get("user").toString(), jsonObject.getJSONArray("cart"));
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

}

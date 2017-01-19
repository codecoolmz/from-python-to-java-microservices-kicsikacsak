package server_controller;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.IOException;

import java.net.URISyntaxException;

/**
 * Created by svindler on 11.01.2017.
 */

public class BannerServiceController {

    private static final String SERVICE_URL = "http://localhost:60000";

    public String getBanner() throws URISyntaxException, IOException {
        StringEntity jsonstring = new StringEntity("{}");

        return Request.Post(SERVICE_URL + "/banner").body(jsonstring)
                .execute().returnContent().asString();
    }

    public String getBannerByUsername() throws URISyntaxException, IOException {
        StringEntity jsonstring = new StringEntity("{user:user, apikey:1234}");

        return Request.Post(SERVICE_URL + "/banner").body(jsonstring)
                .execute().returnContent().asString();
    }

    public String getBannerByUsernameAndCart() throws URISyntaxException, IOException {
        StringEntity jsonstring = new StringEntity("{user:user, apikey:1234, cart: [{name:iphone, category:mobile, defaultprice:100USD, quantity:1}, {name:ledtv, category:tv, defaultprice:200USD, quantity:2}]}}");

        return Request.Post(SERVICE_URL + "/banner").body(jsonstring)
                .execute().returnContent().asString();
    }

}

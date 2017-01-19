package banner_service.Service;

import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by svindler on 11.01.2017.
 */
public class ServiceTest {

    private Service service = Service.getInstance();


    @Test
    public void getInstance() throws Exception {
        Service service1 = Service.getInstance();
        assertEquals(service1, service);
    }

    @Test
    public void getBannerWithEmptyBody() throws Exception {
        assertEquals("done", service.getBanner().get("status"));
    }

    @Test
    public void getBannerWithUsername() throws Exception {
        assertEquals("user", service.getBanner("user").get("user"));
    }


}
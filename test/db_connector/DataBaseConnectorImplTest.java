package db_connector;

import dao.implementation.ProductDaoJbdc;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by doramedgyasszay on 2017. 01. 11..
 */
public class DataBaseConnectorImplTest{
    private ProductDaoJbdc controller = ProductDaoJbdc.getInstance();


    @Test
    public void checkIfDataExists() throws Exception {
        boolean test = controller.checkClientAPIKEY("{client_id:1, name: Testclient, apikey:1234}");
        assertEquals(true, test);
    }

    @Test
    public void checkforInvalidApiKey() throws Exception {
        boolean test = controller.checkClientAPIKEY("{client_id:1, name: Testclient, apikey:1234333}");
        assertEquals(true, test);

    }



}
package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {

    public static final String API_ROOT = "https://api.predic8.de:443/shop";

    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public void getCategories() throws Exception {
        String api_url = API_ROOT + "/categories/";

        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(api_url,JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers() throws Exception {
        String api_url = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(api_url,JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createCustomer() throws Exception {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> params = new HashMap<>();
        params.put("firstname","Joe");
        params.put("lastname","Buck");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl,params,JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void updateCustomer() throws Exception {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> params = new HashMap<>();
        params.put("firstname","Michael");
        params.put("lastname","Weston");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl,params,JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String customerId = customerUrl.split("/")[3];

        System.out.println("Customer ID = " + customerId);

        params.put("firstname","Bozo");
        params.put("lastname","The Clown");

        restTemplate.put(apiUrl + customerId,params);

        JsonNode updateNode = restTemplate.getForObject(apiUrl + customerId,JsonNode.class);

        System.out.println(updateNode.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() throws Exception {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> params = new HashMap<>();
        params.put("firstname","Les");
        params.put("lastname","Claypool");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl,params,JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String customerId = customerUrl.split("/")[3];

        System.out.println("Customer ID = " + customerId);

        restTemplate.delete(apiUrl + customerId);

        System.out.println("Customer with ID " + customerId + " deleted");

        restTemplate.getForObject(apiUrl + customerId,JsonNode.class);
    }
}

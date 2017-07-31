package hello;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.ApplicationConfig;


public class KeyProtectAdapter {
		  
	  private static HttpHeaders createHttpHeaders()
	  {

	    String token = ApplicationConfig.bluemixToken; 
	    String org =  ApplicationConfig.blueMixOrg;   
	    String space = ApplicationConfig.bluemixSpace;

	    HttpHeaders headers = new HttpHeaders();

	    headers.add("bluemix-org", org);
	    headers.add("authorization", token);
	    // correlation-id is an optional param - headers.add("correlation-id", correlation);
	    headers.add("bluemix-space", space);
	    headers.add("accept", "application/json");
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    return headers;
	  }

	  public static String getSecretKeyFromKeyProtectService()
	  {

	    String secretKey = null;

	    String keyProtectSerivceUrl = ApplicationConfig.keyProtectSerivceUrl;
	   
	    //keyId is the parameter to to API.  
	    //When you create a key in Key Protect, the key gets assigned a unique keyId.
	    String keyId = ApplicationConfig.keyId;

	    String theUrl = keyProtectSerivceUrl + keyId;

	    RestTemplate restTemplate = new RestTemplate();
	    
	    try {
	      HttpHeaders headers = createHttpHeaders();
	      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	      ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, String.class);
	    
	      //System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());

	      ObjectMapper mapper = new ObjectMapper();
	      JsonNode rootNode = mapper.readTree(response.getBody());
	      JsonNode resourcesNode = rootNode.path("resources");
	      if (resourcesNode.isArray()) {
	        for (final JsonNode resourceNode : resourcesNode) {
	          // there should only be one resource so 1 entry in the array
	          secretKey = (String) resourceNode.get("payload").textValue();
	        }
	      }
	      
	    }
	    catch (Exception eek) {
	      System.out.println("** Exception: "+ eek.getMessage());
	    }
	    return secretKey;
	  }

}

package files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class AddUpdateGetPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
	    String AddApiresponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(RequestBodies.postRequest())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().asString();
	    
//	    System.out.println(AddApiresponse);
	    
	    JsonPath js = new JsonPath(AddApiresponse);
	    String placeId = js.get("place_id");
	    
	    given().queryParam("key", "qaclick123").queryParam("place_id", placeId).header("Content-Type","application/json")
	    .body("{\r\n"
	    		+ "\"place_id\":\""+placeId+"\",\r\n"
	    		+ "\"address\":\"Shanti-shakti Niwas\",\r\n"
	    		+ "\"key\":\"qaclick123\"\r\n"
	    		+ "}")
	    .when().put("maps/api/place/update/json")
	    .then().statusCode(200).body("msg", equalTo("Address successfully updated"));
	    
	    String updatedJson = given().queryParam("key", "qaclick123").queryParam("place_id", placeId)
	    .when().get("maps/api/place/get/json").then().log().all().assertThat().extract().asString();
	    
	    JsonPath jsnew = new JsonPath(updatedJson);
	    String address = jsnew.get("address");
	    Assert.assertEquals(address, "Shanti-shakti Niwas");
	    
	}

}

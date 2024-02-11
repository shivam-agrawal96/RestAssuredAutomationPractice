package placeApiAutomation;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.RequestBodies;

public class AddPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//given- all input details
		//when - Submit the API
		//then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(RequestBodies.postRequest())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)");

	}

}

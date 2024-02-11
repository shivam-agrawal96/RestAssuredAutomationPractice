package placeApiAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class AddPlaceUsingPojo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// feeding data using getters
		
		AddPlaceBody apb = new AddPlaceBody();
		
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		apb.setLocation(location);
		
		apb.setAccuracy("50");
		apb.setName("Frontline house");
		apb.setAddress("29, side layout, cohen 09");
		apb.setWebsite("http://google.com");
		apb.setLanguage("French-IN");
		
		ItemType type = new ItemType();
		type.setTypes("shoe park");
		type.setTypes("shop");
		
		List<String> itemList = new ArrayList<String>();
		itemList.add("shoe park");
		itemList.add("shop");
		apb.setTypes(itemList);
		
		
		RequestSpecification reqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				setContentType("application/json").addQueryParam("key", "qaclick123").build();
		
		ResponseSpecification resspec= new ResponseSpecBuilder().expectContentType("application/json").expectStatusCode(200).build();
		
		given().spec(reqspec).log().all().body(apb).when().post("/maps/api/place/add/json")
		.then().log().all().spec(resspec).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)");

	}

}

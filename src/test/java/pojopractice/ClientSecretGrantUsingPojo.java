package pojopractice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

public class ClientSecretGrantUsingPojo {

	public static void main(String[] args) {	
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String response = given().
				formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
				formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
				formParam("grant_type", "client_credentials").
				formParam("scope", "trust").
		when().post("oauthapi/oauth2/resourceOwner/token").
		then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String token = js.get("access_token");
		
		GetDetails gd = given().queryParam("access_token", token).
		when().get("oauthapi/getCourseDetails").as(GetDetails.class);
		
		System.out.println(gd.getInstructor());
		System.out.println(gd.getServices());
		System.out.println(gd.getLinkedIn());
		System.out.println(gd.getUrl());
		
		gd.getCourses().getWebAutomation().get(0).getCourseTitle();
		
		
		List<ApiPojo> apiCourses=gd.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
					{
				          System.out.println("price of soapUI = " + apiCourses.get(i).getPrice());
					}
		}
		
		//Get the course names of WebAutomation
		
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"} ;
		ArrayList<String> a= new ArrayList<String>();
		
		List<WebAutomation> w = gd.getCourses().getWebAutomation();
		
		for(int j=0;j<w.size();j++)
		{
			a.add(w.get(j).getCourseTitle());
		}
		
		List<String> expectedList=	Arrays.asList(courseTitles);
		Assert.assertTrue(a.equals(expectedList));
		
		
	}

}

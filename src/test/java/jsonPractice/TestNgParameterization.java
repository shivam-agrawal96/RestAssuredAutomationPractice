package jsonPractice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.AddBookJsonToString;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class TestNgParameterization {
	
	@Test(dataProvider = "booksData")
	public void AddBook(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String addresponse = given().header("Content-Type","application/json").
				body(AddBookJsonToString.addBook(isbn, aisle)).
		when().post("/Library/Addbook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(addresponse);
		JsonPath js = new JsonPath(addresponse);
		String id = js.get("ID");
		System.out.println(id);
		
		String deleteResponse = given().body(AddBookJsonToString.deleteBook(id)).
		when().post("/Library/DeleteBook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(deleteResponse);
		
	}
	
	
	@DataProvider(name ="booksData")
	public Object[][] getData()
	{
		return new Object[][] {{"asasaf", "124115"},{"qettqw","1425"},{"zxvzvzx","87070"}};
	}


}

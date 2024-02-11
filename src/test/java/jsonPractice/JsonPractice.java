package jsonPractice;

import org.testng.Assert;

import files.PracticeJsonFile;
import io.restassured.path.json.JsonPath;

public class JsonPractice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(PracticeJsonFile.PracticeJson());
		int CoursesNo = js.getInt("courses.size()");
		System.out.println(CoursesNo);
		int totalAmount = js.get("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		System.out.println(js.get("courses.title[0]"));
		int sum= 0;
		for (int i =0; i<CoursesNo; i++)
		{
			String title = js.get("courses.title["+i+"]");
			System.out.println(title);
			int price = js.get("courses.price["+i+"]");
			System.out.println(price);
			int copies = js.getInt("courses.copies["+i+"]");
			System.out.println(copies);
			sum = sum + copies*price;
			if ( title.equalsIgnoreCase("RPA"))
			{
				System.out.println("Price of RPA course = " + price);
			}
		}
		
		Assert.assertEquals(sum, totalAmount);

	}
	
	

}

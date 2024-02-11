package files;

public class AddBookJsonToString {
	
	public static String addBook(String isbn, String aisle)
	{
		return "{\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n"
				+ "\"author\":\"Shivam Agrawal\"\r\n"
				+ "}";
	}
	
	public static String deleteBook(String id)
	{
		return "{\r\n"
				+ " \r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ " \r\n"
				+ "}";
	}

}

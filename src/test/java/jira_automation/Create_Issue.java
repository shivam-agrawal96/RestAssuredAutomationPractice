package jira_automation;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class Create_Issue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String comment = "hi how are you afer your new comment";
		//Getting new SessionID
		SessionFilter session = new SessionFilter();
		RestAssured.baseURI = "http://localhost:8080";
		String response = given().header("Content-Type","application/json").body("{ \"username\": \"agrawalshivam926\", "
				+ "\"password\": \"Imsa@2001\" }").filter(session).
		when().post("/rest/auth/1/session").
		then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
		
		//Adding comment on the issue RES-2
		String commentResponse = given().pathParam("key", "10001")
				.header("content-type","application/json").body("{\r\n"
				+ "    \"body\": \""+comment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).
		when().post("/rest/api/2/issue/{key}/comment").
		then().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(commentResponse);
		String commentId = js.get("id").toString();
		
		//Adding attachment to ticket
		given().filter(session).header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data").
		pathParam("key", "10001").multiPart("file", new File(System.getProperty("user.dir")+
				"\\src\\test\\java\\myfile.txt")).
		when().post("/rest/api/2/issue/{key}/attachments").
		then().assertThat().statusCode(200).log().all();
		
		//Getting issue
		String issueComment = given().filter(session).pathParam("key", "10001").queryParam("fields", "comment").
		when().get("/rest/api/2/issue/{key}").
		then().assertThat().statusCode(200).extract().response().asString();
		
		
		//Verifying the comment added is reflecting or not from the issue response
		JsonPath js1 = new JsonPath(issueComment);
		int noOfComments = js1.get("fields.comment.comments.size()");
		
		System.out.println(noOfComments);
		for (int i=0; i<noOfComments; i++)
		{
			String id = js1.get("fields.comment.comments["+i+"].id").toString();
			if (id.equalsIgnoreCase(commentId))
			{
				String verifyComment = js1.get("fields.comment.comments["+i+"].body");
				Assert.assertEquals(verifyComment, comment);
				System.out.println("comment matched");
				break;
			}
		}
		
	}

}

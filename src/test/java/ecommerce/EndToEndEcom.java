package ecommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.EcomLogin;
import pojo.EcomOrderResponse;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class EndToEndEcom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		RequestSpecification loginSpecs = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				setContentType("application/json").build();
		
		EcomLogin login = new EcomLogin();
		login.setUserEmail("shivam.agrawal@gmail.com");
		login.setUserPassword("Imsa@2001");
		
		//Logging In
		RequestSpecification loginRequest  = given().spec(loginSpecs).body(login);
		LoginResponse loginResponse = loginRequest.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		System.out.println(loginResponse.getMessage());
		
		//Creating a new product on products homepages
		RequestSpecification productSpecs = new RequestSpecBuilder().setContentType("multipart/form-data").
				setBaseUri("https://rahulshettyacademy.com").build();
		
		RequestSpecification createProduct = given().spec(productSpecs).header("Authorization",token).
				formParam("productName", "qwerty").
				formParam("productAddedBy", userId).
				formParam("productCategory", "fashion").
				formParam("productSubCategory", "shirts").
				formParam("productPrice", "11500").
				formParam("productDescription", "Addias Originals").formParam("productFor", "women").
				multiPart("productImage", new File("D:\\Resume\\pexels-jeffrey-reed-769749.jpg"));
		
		String createProductResponse = createProduct.when().post("/api/ecom/product/add-product").
				then().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(createProductResponse);
		String ProductId = js.get("productId");
		System.out.println(js.get("message"));
		
		//Creating order for the new product
		RequestSpecification OrderSpecs = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				setContentType("application/json").addHeader("Authorization",token).build();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(ProductId);
		
		List<OrderDetails> orderItems = new ArrayList<OrderDetails>();
		orderItems.add(orderDetails);
		
		Orders orders = new Orders();
		orders.setOrders(orderItems);
		
		RequestSpecification createOrderRequest = given().spec(OrderSpecs).body(orders);
		EcomOrderResponse createOrderResponse = createOrderRequest.when().post("api/ecom/order/create-order").
		then().assertThat().statusCode(201).extract().response().as(EcomOrderResponse.class);
		
		//deserialising create order response
		System.out.println(createOrderResponse.getMessage());
		List<String> orderIds = createOrderResponse.getOrders();
		String orderId = orderIds.get(0);
		
		
		//Deleting the added product;
		RequestSpecification deleteRequestSpecs = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				addHeader("Authorization",token).addPathParam("productId", ProductId).build();
		RequestSpecification deleteRequest = given().spec(deleteRequestSpecs);
		String deleteProductResponse = deleteRequest.when().delete("/api/ecom/product/delete-product/{productId}").
		then().assertThat().statusCode(200).extract().response().asString();
		
		
		JsonPath jsDelete = new JsonPath(deleteProductResponse);
		System.out.println(jsDelete.get("message"));
		
		//Confirming if the order is being placed by getting back the order details
		RequestSpecification viewOrderSpecs = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				addQueryParam("id", orderId).addHeader("Authorization",token).build();
		RequestSpecification viewOrder = given().spec(viewOrderSpecs);
		String viewOrderResponse = viewOrder.when().get("/api/ecom/order/get-orders-details/").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jsOrder = new JsonPath(viewOrderResponse);
		System.out.println(jsOrder.get("message"));
		
		//Deleting the Order from view Orders
		RequestSpecification deleteOrderSpecs = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				addHeader("Authorization",token).addPathParam("orderId", orderId).build();
		RequestSpecification deleteOrder = given().spec(deleteOrderSpecs);
		String deleteOrderResponse = deleteOrder.when().delete("/api/ecom/order/delete-order/{orderId}").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jsDeleteOrder = new JsonPath(deleteOrderResponse);
		System.out.println(jsDeleteOrder.get("message"));
		
	}

}

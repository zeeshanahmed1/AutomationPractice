package stepDefinitions;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import org.junit.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import PojoClasses.*;
import cucumber.deps.com.thoughtworks.xstream.mapper.SystemAttributeAliasingMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyData;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.annotation.JsonUnwrapped;



 

public class Steps {
	
	
	private static final String USER_ID = "f2297db8-28fb-4dcc-9a04-58e17939af67";
	 private static final String USERNAME = "zeeshan ahmed";
	 private static final String PASSWORD = "Paki$tan123";
	 private static final String BASE_URL = "https://bookstore.toolsqa.com";
	 
	 private static String token;
	 private static Response response;
	 private static String jsonString;
	 private static String bookId;
	 
	 
	 Users Obj_User =  new Users();
	 Book Obj_Book = new Book();
	
	@Given("I am an authorized user")
	 public void iAmAnAuthorizedUser() throws JsonProcessingException {
	 
	 RestAssured.baseURI = BASE_URL;
	 RequestSpecification request = RestAssured.given();
	 
	 request.header("Content-Type", "application/json");
	 
	 
	
	 
	 Obj_User.setUserName(USERNAME);
	 Obj_User.setPassword(PASSWORD);
	 
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		String userJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Obj_User);
		//System.out.println("Json Payload is: " + userJson);
	 
	 
	 
	 response = request.body(userJson)
	 .post("/Account/v1/GenerateToken");
	 
	 String jsonString = response.asString();
	 token = JsonPath.from(jsonString).get("token");
	 System.out.println("Token: " + token);
	 
	 System.out.println("____________________________________________________________________________________________________________");
	 
	 
	 }
	 
	
	
	
	 @Given("A list of books are available")
	 public void listOfBooksAreAvailable() {
	 RestAssured.baseURI = BASE_URL;
	 RequestSpecification request = RestAssured.given();
	 response = request.get("/BookStore/v1/Books");
	 
	 jsonString = response.asString();
	 List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
	 Assert.assertTrue(books.size() > 0);
	 
	 bookId = books.get(0).get("isbn"); 
	 
	 
	 System.out.println("List of Books:  " + response.asString());
	 
	 System.out.println("____________________________________________________________________________________________________________");

	 }
	 
	 
	 
	 
	 
	 
	 
	 @When("I add a book to my reading list")
	 public void addBookInList() throws JsonProcessingException {
	 RestAssured.baseURI = BASE_URL;
	 RequestSpecification request = RestAssured.given();
	 request.header("Authorization", "Bearer " + token)
	 .header("Content-Type", "application/json");
	 
	 
	 ObjectMapper objectMapper = new ObjectMapper();
		
	
	Obj_Book = new Book();
	Obj_User = new Users();
	
	Obj_Book.setIsbn(bookId);
	
	List <Book> books = new ArrayList<Book>();
	
	books.add(Obj_Book);
	
	
	Obj_User.setUserId(USER_ID);
	Obj_User.setCollectionOfIsbns(books);
	
	 
	 
		String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Obj_User);
		System.out.println("Json Payload is: " + jsonPayload);
	 
		 response = request.body(jsonPayload)
				 .post("/BookStore/v1/Books");
	 
//	 response = request.body("{ \"userId\": \"" + USER_ID + "\", " +
//	 "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}")
//	 .post("/BookStore/v1/Books");
	
	 
	 System.out.println("Response:  " + response.asString());
	 System.out.println("____________________________________________________________________________________________________________");

	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 @Then("the book is added")
	 public void bookIsAdded() {
		 
	 Assert.assertEquals(201, response.getStatusCode());
	 }
	 
	 
	 
	 
	 
	 @When("I remove a book from my reading list")
	 public void removeBookFromList() throws JsonProcessingException {
	 RestAssured.baseURI = BASE_URL;
	 RequestSpecification request = RestAssured.given();
	 
	 request.header("Authorization", "Bearer " + token)
	 .header("Content-Type", "application/json");
	 
	 ObjectMapper objectMapper = new ObjectMapper();
		
		
		Obj_Book = new Book();
		Obj_User = new Users();
		
			
		
		Obj_User.isbn = new Book();
		Obj_User.isbn.setIsbn(bookId);
		Obj_User.setUserId(USER_ID);
		
		
		 String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Obj_User);
				System.out.println("Jsonn Payload is: " + jsonPayload);
			 

		
		
				// response = request.body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + USER_ID + "\"}")
	
				
				response = request.body(jsonPayload)
	 .delete("/BookStore/v1/Book");
	 
      System.out.println("Response:  " + response.getStatusCode());
      
 	 System.out.println("____________________________________________________________________________________________________________");

	 }
	 
	 @Then("the book is removed")
	 public void bookIsRemoved() {
	 Assert.assertEquals(204, response.getStatusCode());
	 
	 RestAssured.baseURI = BASE_URL;
	 RequestSpecification request = RestAssured.given();
	 
	 request.header("Authorization", "Bearer " + token)
	 .header("Content-Type", "application/json");
	 
	 response = request.get("/Account/v1/User/" + USER_ID);
	 Assert.assertEquals(200, response.getStatusCode());
	 
	 jsonString = response.asString();
	 List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("books");
	 Assert.assertEquals(0, booksOfUser.size());
	 }
	}



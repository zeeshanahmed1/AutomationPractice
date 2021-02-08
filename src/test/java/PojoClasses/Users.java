package PojoClasses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;



@JsonInclude(Include.NON_NULL)
public class Users {
	
	
	private String userName;
	private String password;
	private String userId;
	
	
	 @JsonUnwrapped
	  public Book isbn;
	 
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private List<Book> collectionOfIsbns = null;

	

	public  String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public List<Book> getCollectionOfIsbns() {
		return collectionOfIsbns;
		}

		public void setCollectionOfIsbns(List<Book> collectionOfIsbns) {
		this.collectionOfIsbns = collectionOfIsbns;
		}

}



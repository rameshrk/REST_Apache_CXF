package demo.restful.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import demo.restful.Book;
import demo.restful.Category;


public class CategoryServiceRESTClient {

	//Put some static value
	private static final String CATEGORY_URL="http://localhost:8080/";
	private static final String CATEGORY_ID="005";
	private static final String TYPE_XML="application/xml";
	private static final String TYPE_JSON="application/json";
			
	public static void main(String[] args) {
		
		System.out.println("Format is " + args[0]);
		     
		if(args[0] !=null && args[0].equalsIgnoreCase(TYPE_XML)){
			//Content type- XML
			testAddCategory(TYPE_XML);
			testUpdateCategory(TYPE_XML);
			testGetCategory(TYPE_XML);
			testAddBooksForCategory(TYPE_XML);
			testGetBooksForCategory(TYPE_XML);
			testDeleteCategory(TYPE_XML);
		}
		
		if(args[0] !=null && args[0].equalsIgnoreCase(TYPE_JSON)){
			//ContentType- JSON
			testAddCategory(TYPE_JSON);
			testUpdateCategory(TYPE_JSON);
			testGetCategory(TYPE_JSON);
			testAddBooksForCategory(TYPE_JSON);
			testGetBooksForCategory(TYPE_JSON);
			testDeleteCategory(TYPE_JSON);
		}
		
					
	}
	
	private static void testAddCategory(final String format){
	
		System.out.println("testAddCategory called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/categoryservice/category").accept(
				format).type(format);
		Category cat = new Category();
		cat.setCategoryId(CATEGORY_ID);
		cat.setCategoryName("Fiction");
		Category catResponse = client.post(cat, Category.class);
		System.out.println("Category Id retreived for format " + format + " is "+ catResponse.getCategoryId());
		assertEquals(catResponse.getCategoryId(),CATEGORY_ID);
		
		
	}
	
	private static void testUpdateCategory(final String format){
		
		System.out.println("testUpdateCategory called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/categoryservice/category").accept(
				format).type(format);
		Category cat = new Category();
		cat.setCategoryId(CATEGORY_ID);
		cat.setCategoryName("Fiction Series");
		Response response = client.put(cat);
		System.out.println("Status retreived for update category for format " + format + " is " + response.getStatus());
		assertEquals("200", String.valueOf(response.getStatus()));
		
		
	}
	private static void testGetCategory(final String format){
		
		System.out.println("testGetCategory called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		Category category = client.path("/categoryservice/category/"+CATEGORY_ID).accept(
				format).type(format).get(Category.class);
		System.out.println("Category details retreived from service with format "+ format);
		System.out.println("Category Name " + category.getCategoryName());
		System.out.println("Category Id " + category.getCategoryId());
		assertEquals(CATEGORY_ID, category.getCategoryId());

		
	}
		
	
	private static void testAddBooksForCategory(final String format){
		
		System.out.println("testAddBooksForCategory called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/categoryservice/category/book").type(format).
				accept(format);
		Category cat = new Category();
		cat.setCategoryId(CATEGORY_ID);
		cat.setCategoryName("Fiction Series");
		Book book1 = new Book();
		book1.setAuthor("Naveen Balani");
		book1.setBookId("NB001");
		book1.setBookISBNnumber("ISBNB001");
		book1.setBookName("Fiction Book1");
		
		Collection<Book> booksList = new ArrayList<Book>();
		booksList.add(book1);
		cat.setBooks(booksList);
		client.post(cat, Category.class);
		
				
		
	}
	
	private static void testGetBooksForCategory(final String format){
		
		System.out.println("testGetBooksForCategory called with format " + format);
		WebClient clientBook = WebClient.create(CATEGORY_URL);
		Category categoryBooks = clientBook.path(
				"/categoryservice/category/"+CATEGORY_ID +"/books")
				.type(format).accept(format)
				.get(Category.class);
		System.out.println("Book details retreived from service with format "+ format);
		
		assertEquals(String.valueOf(categoryBooks.getBooks().size()), "1");
		
		Iterator<Book> iterator = categoryBooks.getBooks().iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			System.out.println("Book Name " + book.getBookName());
			System.out.println("Book ISBN " + book.getBookISBNnumber());
			System.out.println("Book ID " + book.getBookId());
			System.out.println("Book Author " + book.getAuthor());

		}
		
		
	}
	
	private static void testDeleteCategory(final String format){
		
		System.out.println("testDeleteCategory called with format " + format);
		WebClient client = WebClient.create(CATEGORY_URL);
		client.path("/categoryservice/category/"+CATEGORY_ID).type(format).
				accept(format);
		Response response = client.delete();
		System.out.println("Status retreived for delete category for format " + format + " is " + response.getStatus());
		assertEquals("200", String.valueOf(response.getStatus()));
		
				
	}
	
	private static void assertEquals(String expected, String result) {
		if(!expected.equalsIgnoreCase(result)){
			throw new RuntimeException("Expecte value " + expected + ", Got value" + result);
		}
	}
	


}

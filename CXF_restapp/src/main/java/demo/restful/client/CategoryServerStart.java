package demo.restful.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import demo.restful.Category;
import demo.restful.CategoryService;

public class CategoryServerStart {

	public static void main(String[] args) {

   
	    ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {
	        "/demo/restful/restapp.xml"
	    });
	    
	    CategoryService categoryService = (CategoryService)
		appContext.getBean("categoryService");
		
		// Service instance

		JAXRSServerFactoryBean restServer = new JAXRSServerFactoryBean();

		restServer.setResourceClasses(Category.class);

		restServer.setServiceBean(categoryService);

		restServer.setAddress("http://localhost:9000/");

		restServer.create();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			br.readLine();
		} catch (IOException e) {

		}
		System.out.println("Server Stopped");
		System.exit(0);

	}

}

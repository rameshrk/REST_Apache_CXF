package demo.restful.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import demo.restful.Category;
import demo.restful.CategoryService;

public class CategoryServerStart {

	public static void main(String[] args) {

		// Service instance

		CategoryService categoryService = new CategoryService();

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

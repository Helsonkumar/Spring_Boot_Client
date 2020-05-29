package com.helson.spring_boot.springboot_client_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@SpringBootApplication
public class SpringbootClientAppApplication {

	Logger logger = LoggerFactory.getLogger("SpringbootClientAppApplication.class");

	public static void main(String[] args) {
		SpringApplication.run(SpringbootClientAppApplication.class, args);
		//getProfile();
	}


	//Uses the Jackson JSON processing library to process the incoming data
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}


	//Runs the RestTemplate .
	//We can use multiple Commandliner beans so they all will get executed.
	@Bean
	public CommandLineRunner getRunner(RestTemplate template) throws Exception{
		return args -> {
			User user = template.getForObject("http://localhost:8080/user/2", User.class);
			logger.info(user.toString());
		};
	}

	@Bean
	public CommandLineRunner postRunner(RestTemplate template) {
		return args -> {
			User user  = new User();
			user.setName("Milan");
			user.setAge(23);
			user.setCity("Kovai");
			user.setSex("Male");

			//This would return the ResponseEntity
			//ResponseEntity<Object> response  = template.postForEntity("http://localhost:8080/user/add" ,user, Object.class);


			//This would return the Reponse Object
			//User user  = template.postForEntity("http://localhost:8080/user/add" ,user, User.class);


			// This would post the resource and return the redirection PATH (Location) of the responseEntity.
			URI location_uri_in_response  = template.postForLocation("http://localhost:8080/user/add" ,user);
            logger.info(location_uri_in_response.toString().replace("/add" ,""));


			//Using the returned location , we redirect and get that user which was just posted.. :)
            User new_user   = template.getForObject(location_uri_in_response.toString().replace("/add" ,"") , User.class);
			logger.info(String.format("Posted the given user %s" , new_user.toString()));
		};
	}


	@Bean
	public CommandLineRunner getRunner2(RestTemplate template) throws Exception{
		return args -> {
			User user = template.getForObject("http://localhost:8080/user/1", User.class);
			logger.info(user.toString());
		};
	}


	//Usually we will not be able to retrive the Container objects like List<Users> from a service response.
	//This is due to runtime type erasure.
	//We can fix this as follows by using the array as body type from the response entity.
	//The List<User> returned from the endpoint is casted to User[].
	//Else  : We can even create a wrapper object which hold this List<User> and have that type defined in the responseType
	// This would  not be an issue if we want to post a List<User>. Here no issue due to type erasure since
	// we convert Java POJO to JSON. So JVM is aware of the type in the List<T>.
	@Bean
	public CommandLineRunner getAllUsers(RestTemplate template) throws Exception{
		return inline_func -> {
			ResponseEntity<User[]> response = template.getForEntity("http://localhost:8080/users", User[].class);
			User[] user_list =  response.getBody();
			Arrays.stream(user_list).forEach(u -> logger.info(String.format("this is the user: %s" , u)));

		};
	}


	/*public static void getProfile() {
		String url  = "http://localhost:8080/profile";
		RestTemplate template = new RestTemplate();
		Profile profile = template.getForObject(url, Profile.class);
		System.out.println(profile);

	}*/


}

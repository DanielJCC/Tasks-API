package com.example.TaskAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:application.properties")
public class TaskApiApplication {
//	@Value("${DB_URL}")
//	private String dbUrl;
//	@Value("${DB_USERNAME}")
//	private String dbUser;
//	@Value("${DB_PASSWORD}")
//	private String dbPassword;
	public static void main(String[] args) {SpringApplication.run(TaskApiApplication.class, args);}
}

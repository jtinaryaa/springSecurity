package com.spring.security.spring_security_basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@Author:Jatin Arora (EAROJAT)
*/
@SpringBootApplication
/*
@Description : This is Spring Security Basic Example.
@FirstThing : As you add the spring security dependency in the pom.xml then spring will throw a LoginPage (for all Request Mapping)
              on the browser , for that you will have to enter the username and password. (If you are using browser)
              If you are using postman then you have to add basic auth with username and password to access the URL/RequestMappings

              Default username will be : user , and password spring will generate in startup logs.
              If you are using the same browser then spring application will not ask you again for credentials , spring will auto
              manage the session , token and other things. If you change the browser then it will ask you the credentials once.

 */
public class SpringSecurityBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBasicApplication.class, args);
	}

}

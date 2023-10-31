package br.senac.pr.exemplospringbootbasicauth;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@SpringBootApplication
public class ExemploSpringBootBasicAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploSpringBootBasicAuthApplication.class, args);
	}

}

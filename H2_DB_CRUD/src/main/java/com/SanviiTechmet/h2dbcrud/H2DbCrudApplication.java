package com.SanviiTechmet.h2dbcrud;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@OpenAPIDefinition(
//        servers = {
//                @Server(url = "http://localhost:8081", description = "Local Server")
//        }
//)
@SpringBootApplication
public class H2DbCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2DbCrudApplication.class, args);
	}

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

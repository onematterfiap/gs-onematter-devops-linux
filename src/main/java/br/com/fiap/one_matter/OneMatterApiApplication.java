package br.com.fiap.one_matter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class OneMatterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneMatterApiApplication.class, args);
	}

}

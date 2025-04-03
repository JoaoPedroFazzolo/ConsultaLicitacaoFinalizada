package br.com.joaopedrofazzolo.itenspregao.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        Contact contact = new Contact();
        contact.setUrl("www.linkedin.com/in/joao-fazzolo");
        contact.name("João Pedro Oliveira Fazzolo");
        Info info = new Info();
        info.title("ItensPregao")
                .version("1.0")
                .description("Aplicação para emitir relatório de uma licitação para importar no sistema da FAB siloms")
                .contact(contact);
        return new OpenAPI().info(info);
    }
}

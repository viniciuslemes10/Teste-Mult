package br.com.api.pedidos.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Pedidos Mult")
                        .description("O Sistema de Gerenciamento de Pedidos permite aos usuários criar e visualizar, além de gerenciar os itens associados a cada pedido.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Vinicius Lemes")
                                .email(" vinikjhgfds@gmail.com")
                                .url("https://github.com/viniciuslemes10/Teste-Mult"))
                        .license(new License()
                                .name("Licença Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}

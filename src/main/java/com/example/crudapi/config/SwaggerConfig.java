package com.example.crudapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Gerenciamento de Pedidos")
                        .version("1.0.0")
                        .description("""
                                API REST completa com CRUD de:
                                - **Categorias** (Category)
                                - **Produtos** (Product) — relacionamento N:1 com Categoria
                                - **Pedidos** (Order) — relacionamento N:M com Produto via OrderItem
                                
                                ### Design Patterns aplicados:
                                - **Strategy**: Cálculo de desconto no total do pedido (NONE, 10%, 20%, 50%)
                                - **Observer**: Notificação de mudança de status do pedido (Log + Estoque)
                                """)
                        .contact(new Contact()
                                .name("Desenvolvedor")
                                .email("dev@example.com"))
                        .license(new License()
                                .name("MIT License")));
    }
}

package com.example.crudapi.init;

import com.example.crudapi.model.Category;
import com.example.crudapi.model.Product;
import com.example.crudapi.repository.CategoryRepository;
import com.example.crudapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Popula o banco H2 com dados iniciais para facilitar os testes no Swagger.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        log.info("=== Inicializando dados de exemplo ===");

        // Categorias
        Category eletronicos = categoryRepository.save(
                Category.builder().name("Eletrônicos").description("Dispositivos eletrônicos e gadgets").build());

        Category vestuario = categoryRepository.save(
                Category.builder().name("Vestuário").description("Roupas, calçados e acessórios").build());

        Category alimentos = categoryRepository.save(
                Category.builder().name("Alimentos").description("Produtos alimentícios e bebidas").build());

        // Produtos — Eletrônicos
        productRepository.save(Product.builder()
                .name("Smartphone Galaxy X10")
                .description("Tela 6.5\", 128GB, câmera 108MP")
                .price(1999.99).stockQuantity(50).category(eletronicos).build());

        productRepository.save(Product.builder()
                .name("Notebook ProBook 15")
                .description("Intel i7, 16GB RAM, SSD 512GB")
                .price(4500.00).stockQuantity(20).category(eletronicos).build());

        productRepository.save(Product.builder()
                .name("Fone Bluetooth ANC")
                .description("Cancelamento de ruído ativo, 30h de bateria")
                .price(299.90).stockQuantity(100).category(eletronicos).build());

        // Produtos — Vestuário
        productRepository.save(Product.builder()
                .name("Camiseta Básica Premium")
                .description("100% algodão, diversas cores")
                .price(59.90).stockQuantity(200).category(vestuario).build());

        productRepository.save(Product.builder()
                .name("Tênis Urban Run")
                .description("Solado amortecido, ideal para corrida")
                .price(349.90).stockQuantity(75).category(vestuario).build());

        // Produtos — Alimentos
        productRepository.save(Product.builder()
                .name("Café Especial 250g")
                .description("Torrado artesanal, grãos arábica selecionados")
                .price(34.90).stockQuantity(150).category(alimentos).build());

        productRepository.save(Product.builder()
                .name("Chocolate Amargo 70%")
                .description("Barra 100g, ingredientes naturais")
                .price(18.50).stockQuantity(300).category(alimentos).build());

        log.info("=== {} categorias e {} produtos criados ===",
                categoryRepository.count(), productRepository.count());
        log.info("=== Swagger UI: http://localhost:8080/swagger-ui.html ===");
        log.info("=== H2 Console:  http://localhost:8080/h2-console ===");
    }
}

package com.amigoscode.product;

import com.amigoscode.SharedPostgresContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@Import({
        ProductService.class
})
@Testcontainers
class ProductServiceTest {

    @Container
    @ServiceConnection
    private static final SharedPostgresContainer POSTGRES =
            SharedPostgresContainer.getInstance();

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService underTest;

    @BeforeAll
    static void beforeAll() {
        System.out.println(POSTGRES.getDatabaseName());
        System.out.println(POSTGRES.getJdbcUrl());
        System.out.println(POSTGRES.getPassword());
        System.out.println(POSTGRES.getDriverClassName());
        System.out.println(POSTGRES.getTestQueryString());
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @Disabled
    void canGetAllProducts() {
        // given
        Product product = new Product(
                UUID.randomUUID(),
                "foo",
                "bardnjknjkndsjknkjnajkndjksandsajkndkjasnkdjank",
                BigDecimal.TEN,
                "https://amigoscode.com/logo.png",
                10
        );

        productRepository.save(product);

        // when
        List<ProductResponse> allProducts =
                underTest.getAllProducts();
        // then
        ProductResponse expected = underTest.mapToResponse().apply(product);

        assertThat(allProducts)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields(
                        "updatedAt", "createdAt"
                )
                .containsOnly(expected);

    }

    @Test
    @Disabled
    void getProductById() {
        // given
        // when
        // then
    }

    @Test
    @Disabled
    void deleteProductById() {
        // given
        // when
        // then
    }

    @Test
    @Disabled
    void saveNewProduct() {
        // given
        // when
        // then
    }

    @Test
    @Disabled
    void updateProduct() {
        // given
        // when
        // then
    }
}
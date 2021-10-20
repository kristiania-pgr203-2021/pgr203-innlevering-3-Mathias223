package no.kristiania.db;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ProductDaoTest {

    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        ProductDao dao = new ProductDao(createDataSource());

        Product product = randomProduct();
        dao.save(product);

        assertThat(dao.retrieve(product.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(product);

    }

}

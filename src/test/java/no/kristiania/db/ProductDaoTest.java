package no.kristiania.db;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {
    ProductDao dao = new ProductDao(ProductDao.createDataSource());

    @Test
    void shouldRetrieveSavedProduct() throws SQLException {

        Product product = randomProduct();
        dao.save(product);

        assertThat(dao.retrieve(product.getId()))
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    private Product randomProduct() {
        Product product = new Product();
        product.setProductName(pickOne("Lego", "Cards", "Musilini", "Candy"));
        return product;
    }

    private String pickOne(String ... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }


}

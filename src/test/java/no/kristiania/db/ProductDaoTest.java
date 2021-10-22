package no.kristiania.db;


import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;


import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {
    private ProductDao dao = new ProductDao(MakeData.createDataSource());

    public ProductDaoTest() throws IOException {
    }


    @Test
    void shouldRetrieveSavedProduct() throws SQLException {

        Product product = randomProduct();
        dao.save(product);

        assertThat(dao.retrieve(product.getId()))
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    void shouldListAllProducts() throws SQLException {
        Product product = randomProduct();
        dao.save(product);
        Product anotherProduct = randomProduct();
        dao.save(anotherProduct);

        assertThat(dao.listAll())
                .extracting(Product::getId)
                .contains(product.getId(), anotherProduct.getId());
    }

    @Test
    void shouldListProductByCategory() throws SQLException {
        Product matchingProduct = randomProduct();
        matchingProduct.setCategoryId(5L);
        dao.save(matchingProduct);
        Product anotherMatchingProduct = randomProduct();
        anotherMatchingProduct.setCategoryId(matchingProduct.getCategoryId());
        dao.save(anotherMatchingProduct);

        Product nonMatchingProduct = randomProduct();
        dao.save(nonMatchingProduct);


        assertThat(dao.listByCategory(matchingProduct.getCategoryId()))
                .extracting(Product::getCategoryId)
                .contains(matchingProduct.getCategoryId(), anotherMatchingProduct.getCategoryId())
                .doesNotContain(nonMatchingProduct.getCategoryId());
    }

    private Product randomProduct() {
        Product product = new Product();
        product.setProductName(MakeData.pickOne("Lego", "Cards", "Orange", "Jawbreaker"));
        product.setProductInfo(MakeData.pickOne("Toy","Toy","Food", "Candy"));
        product.setPrice((int) MakeData.pickOneInt(350, 250, 600, 50));
        product.setCategoryId(MakeData.pickOneInt(1L, 2L, 3L, 4L));
        return product;
    }

}

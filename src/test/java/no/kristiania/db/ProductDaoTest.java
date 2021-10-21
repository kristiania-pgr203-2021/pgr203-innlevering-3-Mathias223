package no.kristiania.db;


import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.sql.SQLException;


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
    void shouldListAllPeople() throws SQLException {
        Product product = randomProduct();
        dao.save(product);
        Product anotherProduct = randomProduct();
        dao.save(anotherProduct);

        assertThat(dao.listAll())
                .extracting(Product::getId)
                .contains(product.getId(), anotherProduct.getId());
    }

    private Product randomProduct() {
        Product product = new Product();
        product.setProductName(MakeData.pickOne("Lego", "Cards", "Orange", "Jawbreaker"));
        product.setProductInfo(MakeData.pickOne("Toy","Toy","Food", "Candy"));
        return product;
    }



}

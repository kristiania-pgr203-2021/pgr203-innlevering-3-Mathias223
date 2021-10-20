package no.kristiania.db;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {
    private ProductDao dao = new ProductDao(ProductDao.createDataSource());

    private DataSource createMigrateTestDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:product;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
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
    void shouldListProductByCategory() throws SQLException {
        Category matchingCategory = randomCategory();
        matchingCategory.setCategoryName("");
        dao.save(matchingCategory);
        Category anotherMatchingCategory = randomCategory();
        anotherMatchingCategory.setCategoryName(matchingCategory.getCategoryName());
        dao.save(anotherMatchingCategory);

        Category nonMatchingCategory = randomCategory();
        dao.save(nonMatchingCategory);


        assertThat(dao.listByCategoryName(matchingCategory.getCategoryName()))
                .extracting(Category::getId)
                .contains(matchingCategory.getId(), anotherMatchingCategory.getId())
                .doesNotContain(nonMatchingCategory.getId());
    }

    private Product randomProduct() {
        Product product = new Product();
        product.setProductName(pickOne("Lego", "Cards", "Musilini", "Jawbreaker"));
        return product;
    }

    //Uncertain if this method is needed
    private Category randomCategory() {
        Category category = new Category();
        category.setCategoryName(pickOne("Toys", "Pasta", "Sweets"));
        return category;
    }

    private String pickOne(String ... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }

}

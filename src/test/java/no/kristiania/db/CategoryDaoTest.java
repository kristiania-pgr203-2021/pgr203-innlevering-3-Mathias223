package no.kristiania.db;

import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryDaoTest {
    private CategoryDao dao = new CategoryDao(MakeData.createDataSource());

    public CategoryDaoTest() throws IOException {
    }

    @Test
    void shouldRetrieveSavedProduct() throws SQLException {

        Category category = randomCategory();
        dao.save(category);

        assertThat(dao.retrieve(category.getId()))
                .usingRecursiveComparison()
                .isEqualTo(category);
    }



    @Test
    void shouldListSavedCategory() throws SQLException {
        Category category1 = randomCategory();
        Category category2 = randomCategory();

        dao.save(category1);
        dao.save(category2);

        assertThat(dao.listAll())
                .contains(category1, category2);
    }

    private Category randomCategory() {
        Category category = new Category();
        category.setCategoryName(MakeData.pickOne("Toys", "Books", "Food", "Candy"));
        return category;
    }

}


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
    void shouldListProductAndCategory(){

    }

/*
    @Test
    void shouldListProductByCategory() throws SQLException {
        Category matchingCategory = MakeData.pickOne("deg", "pd", "hei");
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
    }*/


   @Test
    void shouldListSavedCategory() throws SQLException {
        String category1  = "category-" + UUID.randomUUID();
        String category2 = "category-" + UUID.randomUUID();

        dao.save(category1);
        dao.save(category2);

        assertThat(dao.listAll())
                .contains(category1, category2);
    }

}


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
        /*might no be needed*/
    }


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


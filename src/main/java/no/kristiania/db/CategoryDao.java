package no.kristiania.db;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Category category) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into category (category_name) values (?)", Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, category.getCategoryName());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    category.setId(generatedKeys.getLong("id"));
                }
            }
        }
    }

    public Category retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from category where id = ?")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    return categoryFromRs(resultSet);
                }
            }
        }
    }

    public List<Category> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from category")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Category> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(categoryFromRs(rs));
                    }
                    return result;
                }
            }
        }
    }

    private Category categoryFromRs(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setCategoryName(resultSet.getString("category_name"));
        return category;
    }
}
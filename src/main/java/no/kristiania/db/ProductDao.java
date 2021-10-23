package no.kristiania.db;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Product product) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into product (product_name, product_info, price, category_id ) values (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setString(2, product.getProductInfo());
                preparedStatement.setInt(3, product.getPrice());
                preparedStatement.setLong(4, product.getCategoryId());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    generatedKeys.next();
                    product.setId(generatedKeys.getLong("id"));
                }
            }
        }
    }

    public Product retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from product where id = ?")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    return productFromRs(resultSet);
                }
            }
        }
    }

    public List<Product> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from product")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Product> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(productFromRs(rs));
                    }
                    return result;
                }
            }
        }
    }

    public ArrayList<Product> listByCategory(long categoryId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from product where category_id = ?"
            )) {
                preparedStatement.setLong(1, categoryId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ArrayList<Product> products = new ArrayList<>();
                    while (resultSet.next()) {
                        products.add(productFromRs(resultSet));
                    }
                    return products;
                }
            }
        }
    }

    private Product productFromRs(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setProductInfo(resultSet.getString("product_info"));
        product.setPrice(resultSet.getInt("price"));
        product.setCategoryId(resultSet.getLong("category_id"));
        return product;
    }

}

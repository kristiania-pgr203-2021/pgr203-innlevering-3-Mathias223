package no.kristiania.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class ProductDao {
    private DataSource  dataSource;
    
    public ProductDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/product_db");
        dataSource.setUser("product_dbuser");
        dataSource.setPassword("3G528kHKxL");

        return dataSource;
    }


    public void save(Product product) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into product (product_name) values (?)", Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setString(1,product.getProductName());

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

    private Product productFromRs (ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setProductName(resultSet.getString("product_name"));
        return product;
    }

}

package no.kristiania.db;

import javax.sql.DataSource;
import java.sql.*;

public class ProductDao {
    private DataSource  dataSource;
    
    public ProductDao(DataSource dataSource){
        this.dataSource = dataSource;
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
                    return mapFromResultSet(resultSet);
                }
            }
        }
    }
    public Long retrieve(Long id) {
        return null;
    }


    private Product mapFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setProductName(rs.getString("Product_name"));
        product.setProductInfo(rs.getString("product_info"));
        return product;
    }
}
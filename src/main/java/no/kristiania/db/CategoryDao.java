package no.kristiania.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource){
        this.dataSource =dataSource;
    }

    public void save(String categoryName) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into category (category_name) values (?)"
            )) {
                statement.setString(1, categoryName);
                statement.executeUpdate();
            }
        }
    }

    public List<String> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from category")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<String> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(rs.getString("category_name"));
                    }
                    return result;
                }
            }
        }
    }
}
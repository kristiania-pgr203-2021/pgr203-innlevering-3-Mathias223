package no.kristiania.db;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.util.Properties;
import java.util.Random;

public class MakeData {
    public static DataSource createDataSource() throws IOException{
        /*
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:productdb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();

        propertis.load(FileReader("src/main/resources/config.properties"))

        return dataSource ;
*/
        PGSimpleDataSource dataSource = new PGSimpleDataSource();



            try (InputStream input = ProductDao.class.getClassLoader().getResourceAsStream("config.properties")) {

                Properties prop = new Properties();

                if (input == null) {
                    System.out.println("Sorry, unable to find config.properties");


                }
                prop.load(input);
                System.out.println(prop.getProperty("db.password"));
                dataSource.setPassword(prop.getProperty("db.password"));
                dataSource.setUser(prop.getProperty("db.user"));
                dataSource.setURL(prop.getProperty("db.url"));

        }
        return dataSource;
    }
    private static Random random = new Random();

    public static String pickOne(String ... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }

}

package com.cooper.article.data.repo;

import com.cooper.article.model.FeedSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedSourceRepo implements FeedSourceDao {

    private static final String DB_PROP_FILE = "/db.properties";

    private static final Logger logger = LogManager.getLogger(FeedSourceRepo.class);

    private final HikariConfig config = new HikariConfig(DB_PROP_FILE);
    private final HikariDataSource dataSource = new HikariDataSource(config);

    private Connection con;
    PreparedStatement pst;

    @Override
    public List<FeedSource> getSources() {
        try{

            // get a connection from the pool
            con = dataSource.getConnection();

            // statement to execute
            pst = con.prepareStatement("SELECT feed_name, feed_url FROM feeds");

            // go!
            ResultSet set = pst.executeQuery();

            var results = new ArrayList<FeedSource>();

            while(set.next()){
                try {
                    results.add(new FeedSource(set.getString(1), set.getString(2)));
                } catch (MalformedURLException e) {
                    logger.error(String.format("Invalid URL for feed source with name: %s", set.getString(1)), e);
                }
            }

            return results;

        } catch(SQLException ex){
            logger.error(ex);
            return new ArrayList<>();
        }
    }
}

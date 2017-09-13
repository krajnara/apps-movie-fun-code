package org.superbiz.moviefun.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by 103209 on 13/09/17.
 */
@Configuration
public class DBConfiguration {
    @Bean(name  = "movies-ds")
    @ConfigurationProperties("moviefun.datasources.movies")
    public DataSource moviesDataSource() {
        DataSource ds = DataSourceBuilder.create().type(HikariDataSource.class).build();
//        HikariConfig conf = new HikariConfig();
//        conf.setDataSource(ds);
//        conf.setJdbcUrl();
//        return new HikariDataSource(conf);
        return ds;
    }

    @Bean(name = "albums-ds")
    @ConfigurationProperties("moviefun.datasources.albums")
    public DataSource albumsDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}

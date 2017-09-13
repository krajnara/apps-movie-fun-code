package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean actionServletRegistration(ActionServlet actionServlet) {
        return new ServletRegistrationBean(actionServlet, "/moviefun/*");
    }

    @Bean
    public HibernateJpaVendorAdapter createAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        adapter.setGenerateDdl(true);

        return adapter;
    }

    @Bean(name = "movies-factory")
    public LocalContainerEntityManagerFactoryBean createMoviesEntityManager(
            @Qualifier("movies-ds") DataSource source, HibernateJpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(source);
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("org.superbiz.moviefun");
        factoryBean.setPersistenceUnitName("unit-movies");

        return factoryBean;
    }

    @Bean(name = "albums-factory")
    public LocalContainerEntityManagerFactoryBean createAlbumsEntityManager(
            @Qualifier("albums-ds") DataSource source, HibernateJpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(source);
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("org.superbiz.moviefun");
        factoryBean.setPersistenceUnitName("unit-albums");

        return factoryBean;
    }

    @Bean(name = "movies-manager")
    public PlatformTransactionManager movieTransactionManager(
            @Qualifier("movies-factory") LocalContainerEntityManagerFactoryBean bean) {
        PlatformTransactionManager mgr = new JpaTransactionManager(bean.getObject());
        return mgr;
    }

    @Bean(name = "albums-manager")
    public PlatformTransactionManager albumTransactionManager(
            @Qualifier("albums-factory") LocalContainerEntityManagerFactoryBean bean) {
        PlatformTransactionManager mgr = new JpaTransactionManager(bean.getObject());
        return mgr;
    }
}

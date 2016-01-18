package tasklist.spring.controller;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import tasklist.spring.manager.TasksRepositoryManager;
import tasklist.spring.repository.TasksRepository;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan(basePackages="tasklist.spring.repository")
@EnableJpaRepositories(basePackages = "tasklist.spring.repository")
@Configuration
public class AppConfiguration {

    @Bean
    public DataSource dataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setURL("jdbc:mysql://localhost:3306/taskslist");
        dataSource.setDatabaseName( "tasklist" );
        dataSource.setUser( "root" );
        dataSource.setPassword("root");

        return dataSource;
    }

    @Bean
    public Properties hibernateProperties(){
        final Properties properties = new Properties();

        properties.put( "hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put( "hibernate.connection.url", "jdbc:mysql://localhost:3306/taskslist" );
        properties.put( "hibernate.connection.username", "root" );
        properties.put( "hibernate.connection.password", "root" );
        properties.put( "hibernate.connection.driver_class", "com.mysql.jdbc.Driver" );
        properties.put( "hibernate.hbm2ddl.auto", "update" );

        return properties;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties ){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource( dataSource );
        em.setPackagesToScan( "tasklist.spring.entities" );
        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
        em.setJpaProperties( hibernateProperties );
        em.setPersistenceUnitName( "tasklistJPA" );
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();

        return em.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory  em) {
        JpaTransactionManager jtm = new JpaTransactionManager();
        jtm.setEntityManagerFactory(em);
        return jtm;
    }

    @Bean
    public TasksRepositoryManager tasksRepositoryManager(TasksRepository tasksRepository) {
        TasksRepositoryManager repositoryManager = new TasksRepositoryManager();
        repositoryManager.setTasksRepository(tasksRepository);
        return repositoryManager;
    }
}

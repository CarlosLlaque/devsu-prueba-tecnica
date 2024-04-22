package com.cllaque.personams.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DBConfig {
    // La configuracion por defecto encuentra el schema.sql y lo ejecuta
    //
    // @Bean
    // ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

    //     ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    //     initializer.setConnectionFactory(connectionFactory);
    //     initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

    //     return initializer;
    // }
    //
    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

}

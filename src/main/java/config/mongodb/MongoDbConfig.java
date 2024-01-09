package config.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoDbConfig
{

    @Bean
    public MongoTemplate mongoTemplate()
    {
        SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/ImmobilienDB");
        MongoTemplate mongoTemplate = new MongoTemplate(factory);

        return mongoTemplate;
    }
}

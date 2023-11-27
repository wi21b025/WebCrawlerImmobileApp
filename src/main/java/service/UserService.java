package service;

import database.MongoDbConfig;
import model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoDbConfig.class);
    MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

    public List<String> getAllUserEmails() {
        List<User> users = mongoTemplate.find(new Query(), User.class);
        return users.stream().map(User::getEmail).collect(Collectors.toList());
    }

}

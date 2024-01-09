package service;

import config.mongodb.MongoDbConfig;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import web.dto.FilterDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoDbConfig.class);
    MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

    public List<String> getAllUserEmails() {
        List<User> users = mongoTemplate.find(new Query(), User.class);
        return users.stream().map(User::getEmail).collect(Collectors.toList());
    }
    public List<User> getAllUsers() {
        return mongoTemplate.find(new Query(), User.class);
    }
    public boolean saveUser(User user) {
        try {
            return saveIfNotExists(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Indicate failure in case of exception
        }
    }

    public boolean isUserExists(User user)
    {
        return mongoTemplate.exists(Query.query(
                        Criteria.where("username").is(user.getUsername())
                                .orOperator(Criteria.where("email").is(user.getEmail()))), User.class);
    }

    public boolean saveIfNotExists(User user)
    {
        if (!isUserExists(user)) {
            mongoTemplate.save(user);
            logger.info("New User is saved in the DB successfully.");
            return true; // New user saved
        } else {
            logger.info("User with the same username or email already exists.");
            return false; // User with the same username or email already exists
        }
    }

    public User findByUsernameOrEmail(String usernameOrEmail)
    {

        return mongoTemplate.findOne(
                Query.query(
                        new Criteria().orOperator(
                                Criteria.where("username").is(usernameOrEmail),
                                Criteria.where("email").is(usernameOrEmail)
                        )
                ),
                User.class
        );
    }

    public boolean saveUserFilter(String userEmail, FilterDTO filterDTO) {
        try {
            // Find the existing user by email
            User user = findByEmail(userEmail);

            // If user doesn't exist, handle accordingly (create new or return false)
            if (user == null) {
                // Handle the case where the user is not found.
                // For example, you might want to return false or throw an exception.
                return false;
            }

            // Convert FilterDTO to Map and set it to the user
            Map<String, Object> filterMap = filterDTO.toMap();
            user.setFilter(filterMap);

            // Save the user with the new filter to the database
            mongoTemplate.save(user);

            return true; // Indicate success
        } catch (Exception e) {
            // Handle exceptions, such as connection issues or constraints violations
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    // Helper method to find user by email
    private User findByEmail(String email) {
        return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)), User.class);
    }

}

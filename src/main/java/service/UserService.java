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

import java.util.*;
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
        if (!isUserExists(user))
        {
            mongoTemplate.save(user);
            logger.info("New User is saved in the DB successfully.");
            return true;
        }
        else
        {
            logger.info("User with the same username or email already exists.");
            return false;
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

    public boolean saveUserFilters(String userEmail, List<FilterDTO> filterDTOList) {
        try {
            // Find the existing user by email
            User user = findByEmail(userEmail);

            // If user doesn't exist, handle accordingly (create new or return false)
            if (user == null) {
                // Handle the case where the user is not found.
                // For example, you might want to return false or throw an exception.
                return false;
            }

            // Convert List of FilterDTOs to a List of Maps
            List<Map<String, Object>> filterMaps = new ArrayList<>();
            for (FilterDTO filterDTO : filterDTOList) {
                filterMaps.add(filterDTO.toMap());
            }

            // Set the list of filter maps to the user
            user.setFilters(filterMaps);

            // Save the user with the new filters to the database
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

    public List<FilterDTO> getUserFilters(String email) {
        User user = findByEmail(email);
        if (user != null && user.getFilters() != null) {
            List<Map<String, Object>> filterList = user.getFilters();
            List<FilterDTO> filterDTOList = new ArrayList<>();

            for (Map<String, Object> filterMap : filterList) {
                filterDTOList.add(convertMapToFilterDTO(filterMap));
            }

            return filterDTOList;
        }
        return Collections.emptyList();
    }




    // Helper method to convert Map to FilterDTO
    private FilterDTO convertMapToFilterDTO(Map<String, Object> filterMap) {
        logger.info("Converting filterMap to FilterDTO: {}", filterMap); // Add this logging

        if (filterMap == null) {
            return null; // Or handle the null case as needed
        }

        FilterDTO filterDTO = new FilterDTO();

        filterDTO.setBundesland((String) filterMap.get("bundesland"));
        filterDTO.setOrt((String) filterMap.get("ort"));
        filterDTO.setBezirk(filterMap.get("bezirk") != " " ? (String) filterMap.get("bezirk") : "");
        filterDTO.setPreis_from((String) filterMap.get("preis_von"));
        filterDTO.setPreis_to((String) filterMap.get("preis_bis"));
        filterDTO.setArea_from((String) filterMap.get("fläche_von"));
        filterDTO.setArea_to((String) filterMap.get("fläche_bis"));
        filterDTO.setPreis_sq_2_from((String) filterMap.get("preis_sq_2_von"));
        filterDTO.setPreis_sq_2_to((String) filterMap.get("preis_sq_2_bis"));
        filterDTO.setKategorie((String) filterMap.get("kategorie"));

        // Assuming "uid" is part of the map
        filterDTO.setUid((String) filterMap.get("uid"));

        return filterDTO;
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

            // Convert FilterDTO to Map and set it to the user's filters list
            Map<String, Object> filterMap = filterDTO.toMap();
            List<Map<String, Object>> userFilters = user.getFilters();

            if (userFilters == null) {
                userFilters = new ArrayList<>();
            }

            userFilters.add(filterMap);
            user.setFilters(userFilters);

            // Save the user with the updated filters to the database
            mongoTemplate.save(user);

            return true; // Indicate success
        } catch (Exception e) {
            // Handle exceptions, such as connection issues or constraints violations
            e.printStackTrace();
            return false; // Indicate failure
        }
    }
    public FilterDTO getFilterByUid(String uid) {
        try {
            // Query MongoDB to find the filter by uid
            Query query = new Query(Criteria.where("filters.uid").is(uid));
            User user = mongoTemplate.findOne(query, User.class);

            // Check if the user and filter data exist
            if (user != null && user.getFilters() != null) {
                List<Map<String, Object>> filterList = user.getFilters();

                // Find the filter with the matching uid
                Optional<Map<String, Object>> matchingFilter = filterList.stream()
                        .filter(filterMap -> uid.equals(filterMap.get("uid")))
                        .findFirst();

                if (matchingFilter.isPresent()) {
                    // Convert the matching filter map to a FilterDTO
                    FilterDTO filterDTO = convertMapToFilterDTO(matchingFilter.get());
                    return filterDTO;
                }
            }

            // Handle the case when the filter data is not found
            return null;
        } catch (Exception e) {
            // Handle exceptions, such as connection issues or constraints violations
            e.printStackTrace();
            return null;
        }
    }
    // In UserService class

    public boolean deleteUserFilter(String userEmail, String filterUid) {
        User user = findByEmail(userEmail);
        if (user != null) {
            List<Map<String, Object>> filters = user.getFilters();
            if (filters != null) {
                boolean isRemoved = filters.removeIf(filter -> filterUid.equals(filter.get("uid")));
                if (isRemoved) {
                    mongoTemplate.save(user); // Save the user after removing the filter
                    return true;
                }
            }
        }
        return false;
    }

}

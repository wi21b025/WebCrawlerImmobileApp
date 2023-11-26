package service;

import database.MongoDbConfig;
import model.Immobile;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImmobileService {

    //private final MongoTemplate mongoTemplate;

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoDbConfig.class);
    MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

    /*   @Autowired
       public ImmobileService(MongoTemplate mongoTemplate) {
           this.mongoTemplate = mongoTemplate;
       }
   */
    public void saveImmobile(Immobile immobile)
    {
        try {
            saveIfNotExists(immobile);
        }catch (Exception e) {
            // Handle any exceptions that occur during initialization
            e.printStackTrace(); // You can log the exception or perform other actions here
        }
        //mongoTemplate.save(immobile);
    }



   /* public void initializeImmobile() {
        try {
            // Create and save Immobile objects here
            Immobile immobile1 = new Immobile("House", "Beautiful House", "$30,000", "123 Main St", "200 sq. ft", "image1.jpg","");
            Immobile immobile2 = new Immobile("Apartment", "Cozy Apartment", "$15,000", "456 Elm St", "150 sq. ft", "image2.jpg","");

            // Save the Immobile objects using the service if they do not already exist
            saveIfNotExists(immobile1);
            saveIfNotExists(immobile2);
        } catch (Exception e) {
            // Handle any exceptions that occur during initialization
            e.printStackTrace(); // You can log the exception or perform other actions here
        }
    }

    */



    public Immobile createImmobile(String category, String address, String size) {
        Immobile immobile = new Immobile();
        immobile.setCategory(category);
        immobile.setAddress(address);
        immobile.setSize(size);
        return immobile;
    }

    public boolean isImmobileExists(Immobile immobile) {
        return mongoTemplate.exists(Query.query(
                        Criteria.where("category").is(immobile.getCategory())
                                .and("address").is(immobile.getAddress())
                                .and("size").is(immobile.getSize())),
                Immobile.class);
    }

    public void saveIfNotExists(Immobile immobile) {
        Immobile existingImmobile = mongoTemplate.findOne(
                Query.query(
                        Criteria.where("category").is(immobile.getCategory())
                                .and("address").is(immobile.getAddress())
                                .and("size").is(immobile.getSize())
                ),
                Immobile.class
        );

        if (existingImmobile == null) {
            // The Immobile does not exist in the database, so we save it.
            mongoTemplate.save(immobile);
            System.out.println("Immobile saved successfully.");
        } else {
            if (!existingImmobile.getPrice().equals(immobile.getPrice())) {
                existingImmobile.setPrice(immobile.getPrice());
                mongoTemplate.save(existingImmobile);
                System.out.println("Immobile price updated.");
            } else {
                System.out.println("This immobile is already in the database with the same price.");
            }
        }
    }


    public List<Immobile> searchByCategory(String category) {
        return mongoTemplate.find(Query.query(Criteria.where("category").is(category)), Immobile.class);
    }

    public List<Immobile> searchByAddress(String address) {
        return mongoTemplate.find(Query.query(Criteria.where("address").is(address)), Immobile.class);
    }

    public List<Immobile> searchBySize(String size) {
        return mongoTemplate.find(Query.query(Criteria.where("size").is(size)), Immobile.class);
    }

    public Optional<Immobile> searchByCategoryAndAddressAndSize(String category, String address, String size) {
        return Optional.ofNullable(mongoTemplate.findOne(Query.query(
                        Criteria.where("category").is(category)
                                .and("address").is(address)
                                .and("size").is(size)),
                Immobile.class));
    }
}
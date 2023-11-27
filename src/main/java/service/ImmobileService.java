package service;

import database.MongoDbConfig;
import model.Immobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImmobileService
{

    //private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ImmobileService.class);

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoDbConfig.class);
    MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

    /*   @Autowired
       public ImmobileService(MongoTemplate mongoTemplate) {
           this.mongoTemplate = mongoTemplate;
       }
   */
    public boolean saveImmobile(Immobile immobile)
    {
        try
        {
            return saveIfNotExists(immobile); // Return the result of saveIfNotExists
        } catch (Exception e)
        {
            e.printStackTrace();
            return false; // Indicate failure in case of exception
        }
    }



   /* public void initializeImmobile() {
        try {
            // Create and save Immobile objects here
            Immobile immobile1 = new Immobile("House", "Beautiful House", "$30,000", "123 Main St", "200 sq. ft", "image1.jpg","");
            Immobile immobile2 = new Immobile("Apartment", "Cozy Apartment", "$15,000", "456 Elm St", "150 sq. ft", "image2.jpg","");
            saveIfNotExists(immobile1);
            saveIfNotExists(immobile2);
        } catch (Exception e) {
            // Handle any exceptions that occur during initialization
            e.printStackTrace();
        }
    }

    */



    public Immobile createImmobile(String category, String address, String size)
    {
        Immobile immobile = new Immobile();
        immobile.setCategory(category);
        immobile.setAddress(address);
        immobile.setSize(size);
        return immobile;
    }

    public boolean isImmobileExists(Immobile immobile)
    {
        return mongoTemplate.exists(Query.query(
                        Criteria.where("category").is(immobile.getCategory())
                                .and("address").is(immobile.getAddress())
                                .and("size").is(immobile.getSize())),
                Immobile.class);
    }

    public boolean saveIfNotExists(Immobile immobile)
    {
        Immobile existingImmobile = mongoTemplate.findOne(
                Query.query(
                        Criteria.where("category").is(immobile.getCategory())
                                .and("address").is(immobile.getAddress())
                                .and("size").is(immobile.getSize())
                ),
                Immobile.class
        );

        if (existingImmobile == null)
        {
            mongoTemplate.save(immobile);
            logger.info("New Immobile are saved in the DB successfully.");
            return true; // New immobile saved
        }
        else
        {
            if (!existingImmobile.getPrice().equals(immobile.getPrice()))
            {
                existingImmobile.setPrice(immobile.getPrice());
                mongoTemplate.save(existingImmobile);
                logger.info("Immobile price updated.");

                return true; // Existing immobile updated
            }
            else
            {
                logger.info("This immobile is already in the database with the same price.");

                return false; // No new information, not considered new
            }
        }
    }

    public List<Immobile> searchByCategory(String category)
    {
        return mongoTemplate.find(Query.query(Criteria.where("category").is(category)), Immobile.class);
    }

    public List<Immobile> searchByAddress(String address)
    {
        return mongoTemplate.find(Query.query(Criteria.where("address").is(address)), Immobile.class);
    }

    public List<Immobile> searchBySize(String size)
    {
        return mongoTemplate.find(Query.query(Criteria.where("size").is(size)), Immobile.class);
    }

    public Optional<Immobile> searchByCategoryAndAddressAndSize(String category, String address, String size)
    {
        return Optional.ofNullable(mongoTemplate.findOne(Query.query(
                        Criteria.where("category").is(category)
                                .and("address").is(address)
                                .and("size").is(size)),
                Immobile.class));
    }

}
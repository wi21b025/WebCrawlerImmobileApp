package notification;

import model.Immobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private static Map<String, Object> getTemplateModel(List<Immobile> newImmobiles) {
        logger.info("Creating template model for email...");
        // Create and return your template model here
        Map<String, Object> model = new HashMap<>();

        // Filter the new immobiles based on some criteria
        List<Map<String, String>> newImmobileListings = new ArrayList<>();
        for (Immobile newImmobile : newImmobiles) {
            logger.info("Immobile: " + newImmobile.getTitle() + ", Rooms: " + newImmobile.getRoom() + ", Size: " + newImmobile.getSize());


            Map<String, String> listing = new HashMap<>();
            listing.put("title", newImmobile.getTitle());
            listing.put("imageUrl", newImmobile.getImageUrl());
            listing.put("price", newImmobile.getPrice());
            listing.put("address", newImmobile.getAddress());
            listing.put("rooms", newImmobile.getRoom());
            listing.put("sizes", newImmobile.getSize());
            listing.put("pricesqm", newImmobile.getPreisProSqMeter());
            listing.put("viewLink", newImmobile.getViewLink());
            newImmobileListings.add(listing);
        }

        // Add the list of new listings to the model
        model.put("listings", newImmobileListings);

        logger.info("Template model created successfully.");
        return model;
    }

    public static void sendEmail(String recipient, String subject, List<Immobile> newImmobileListings) throws MessagingException {
        logger.info("Sending email...");

        Properties properties = new Properties();

        // Set SMTP server properties
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Your email and app password
        final String myAccountEmail = "crawlerimmobilien@gmail.com"; // Replace with your email
        final String password = "vglcncomizadisdl";         // Replace with your app password

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Map<String, Object> templateModel = getTemplateModel(newImmobileListings);

        Message message = prepareMessage(session, myAccountEmail, recipient, subject, templateModel);

        Transport.send(message);
        logger.info("Email sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, Map<String, Object> templateModel) {
        try {
            logger.info("Preparing email message...");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            // Set up Thymeleaf template engine with ClassLoaderTemplateResolver
            TemplateEngine templateEngine = new TemplateEngine();
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

            templateResolver.setPrefix("/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode(TemplateMode.HTML);
            templateResolver.setCharacterEncoding("UTF-8");
            templateEngine.setTemplateResolver(templateResolver);

            // Prepare the Thymeleaf context with your data model
            Context context = new Context();
            context.setVariables(templateModel);

            // Process the Thymeleaf template
            String htmlContent = templateEngine.process("email-template", context);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            logger.info("Email message prepared successfully.");
            return message;
        } catch (Exception ex) {
            logger.error("Error preparing email message: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}

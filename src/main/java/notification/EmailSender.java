package notification;

public class EmailSender
{
/*
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter recipient's email address:");
        String recipient = scanner.nextLine();

        System.out.println("Enter subject of the email:");
        String subject = scanner.nextLine();

        // Assuming you have some method to generate your template model
        Map<String, Object> templateModel = getTemplateModel();

        try {
            sendEmail(recipient, subject, templateModel);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> getTemplateModel() {
        // Create and return your template model here
        Map<String, Object> model = new HashMap<>();

        // Example list of listings
        List<Map<String, String>> listings = new ArrayList<>();

        // Example listing 1
        Map<String, String> listing1 = new HashMap<>();
        listing1.put("title", "Modern City Apartment");
        listing1.put("imageUrl", "https://cache.willhaben.at/mmo/0/732/581/770_1089917387_hoved.jpg"); // Replace with actual image URL
        listing1.put("price", "$300,000");
        listing1.put("address", "123 Main St, Metropolis");
        listing1.put("size", "1200 sqft");
        listings.add(listing1);

        // Example listing 2
        Map<String, String> listing2 = new HashMap<>();
        listing2.put("title", "Cozy Country Home");
        listing2.put("imageUrl", "https://cache.willhaben.at/mmo/6/740/527/466_-2037459382_hoved.jpg"); // Replace with actual image URL
        listing2.put("price", "$250,000");
        listing2.put("address", "456 Country Rd, Smallville");
        listing2.put("size", "2000 sqft");
        listings.add(listing2);

        // Add the list of listings to the model
        model.put("listings", listings);

        return model;
    }

    public static void sendEmail(String recipient, String subject, Map<String, Object> templateModel) throws MessagingException {
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

        Message message = prepareMessage(session, myAccountEmail, recipient, subject, templateModel);

        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String subject, Map<String, Object> templateModel) {
        try {
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

            return message;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

 */

}

package at.refugeescode.profilesprojectdemo.configuration;
import at.refugeescode.profilesprojectdemo.persistance.model.EmailService;
import at.refugeescode.profilesprojectdemo.persistance.model.Mail;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class MailConfiguration {

        private static Logger log = LoggerFactory.getLogger(Application.class);

        @Autowired
        private EmailService emailService;

        @Bean
        ApplicationRunner applicationRunner() throws Exception{
            return applicationArguments -> {
                log.info("Spring Mail - Sending Email with Inline Attachment Example");

                Mail mail = new Mail();
                mail.setFrom("no-reply@memorynotfound.com");
                //set the person name that you want to send to.
                mail.setTo("mohammadalmosleh66@gmail.com");
                mail.setSubject("Sending Email with Inline Attachment Example");
                mail.setContent("welcome bro");

                emailService.sendSimpleMessage(mail);
            };
        }
    }


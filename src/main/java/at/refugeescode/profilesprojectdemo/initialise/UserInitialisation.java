package at.refugeescode.profilesprojectdemo.initialise;

import at.refugeescode.profilesprojectdemo.persistance.model.Admin;
import at.refugeescode.profilesprojectdemo.persistance.repository.AdminRepository;
import at.refugeescode.profilesprojectdemo.persistance.model.Company;
import at.refugeescode.profilesprojectdemo.persistance.repository.CompanyRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class UserInitialisation {

    @Bean
    ApplicationRunner initialiseUsers(PasswordEncoder passwordEncoder, CompanyRepository companyRepository, AdminRepository adminRepository) {
        return args -> {
            Company company = new Company();
            company.setUsername("user");
           company.setPassword(passwordEncoder.encode("user"));
           company.setAuthorities(Stream.of("USER").collect(Collectors.toSet()));
           companyRepository.save(company);

           Admin admin = new Admin();
           admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setAuthorities(Stream.of("ADMIN").collect(Collectors.toSet()));
            adminRepository.save(admin);
        };
    }
}

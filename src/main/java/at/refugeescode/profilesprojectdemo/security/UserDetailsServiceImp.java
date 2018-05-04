package at.refugeescode.profilesprojectdemo.security;

import at.refugeescode.profilesprojectdemo.Company;
import at.refugeescode.profilesprojectdemo.CompanyRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private CompanyRepository companyRepository;

    public UserDetailsServiceImp(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Company> company = companyRepository.findOneByUsername(username);
        if (!company.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(company.get());
    }
}
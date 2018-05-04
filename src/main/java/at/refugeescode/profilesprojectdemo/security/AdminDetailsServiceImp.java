package at.refugeescode.profilesprojectdemo.security;

import at.refugeescode.profilesprojectdemo.persistance.model.Admin;
import at.refugeescode.profilesprojectdemo.persistance.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsServiceImp implements UserDetailsService {

    private AdminRepository adminRepository;

    public AdminDetailsServiceImp(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Admin> admin = adminRepository.findOneByUsername(username);
        if (!admin.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new AdminPrincipal(admin.get());
    }
}
package at.refugeescode.profilesprojectdemo.persistance.repository;

import at.refugeescode.profilesprojectdemo.persistance.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findOneByUsername(String username);
}

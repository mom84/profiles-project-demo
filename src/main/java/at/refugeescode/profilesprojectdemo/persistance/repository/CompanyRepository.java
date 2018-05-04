package at.refugeescode.profilesprojectdemo.persistance.repository;

import at.refugeescode.profilesprojectdemo.persistance.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>{
    Optional<Company> findByName(String name);
    Optional<Company> findOneByUsername(String username);
}

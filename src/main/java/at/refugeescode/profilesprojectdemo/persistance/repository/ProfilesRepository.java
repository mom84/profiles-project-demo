package at.refugeescode.profilesprojectdemo.persistance.repository;

import at.refugeescode.profilesprojectdemo.persistance.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilesRepository extends JpaRepository<Participant, Long> {
}

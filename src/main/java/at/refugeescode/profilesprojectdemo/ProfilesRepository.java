package at.refugeescode.profilesprojectdemo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilesRepository extends JpaRepository<Participant, Long> {
}

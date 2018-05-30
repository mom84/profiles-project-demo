package at.refugeescode.profilesprojectdemo.persistance.model;

import at.refugeescode.profilesprojectdemo.persistance.repository.CompanyRepository;
import at.refugeescode.profilesprojectdemo.persistance.repository.ProfilesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class Interests {

    private CompanyRepository companyRepository;

    private ProfilesRepository profilesRepository;

    public Interests(CompanyRepository companyRepository, ProfilesRepository profilesRepository) {
        this.companyRepository = companyRepository;
        this.profilesRepository = profilesRepository;
    }

    public void interested(Long companyId, Long participantId) {
        Company company = companyRepository.findById(companyId).get();
        Participant participant = profilesRepository.findById(participantId).get();
        company.getInterested().add(participant);
        companyRepository.save(company);
    }

    public void notInterested(Long companyId, Long participantId) {
        Company company = companyRepository.findById(companyId).get();
        Participant participant = profilesRepository.findById(participantId).get();
        company.getInterested().remove(participant);
        companyRepository.save(company);
    }

    public List<Participant> getInterestedParticipants(Long userId) {
        return companyRepository.findById(userId).get().getInterested().stream()
                .collect(Collectors.toList());
    }
}

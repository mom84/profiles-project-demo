package at.refugeescode.profilesprojectdemo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class ProfilesEndpoint {

    private ProfilesRepository profilesRepository;

    private CompanyRepository companyRepository;

    private AdminRepository adminRepository;

    private PasswordEncoder passwordEncoder;

    private Participant participant;

    private List<Participant> participants = new ArrayList<>();

    public ProfilesEndpoint(ProfilesRepository profilesRepository, CompanyRepository companyRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder, Participant participant, List<Participant> participants) {
        this.profilesRepository = profilesRepository;
        this.companyRepository = companyRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.participant = participant;
        this.participants = participants;
    }

    @GetMapping("/")
    String page1(){
        return "showParticipants";
    }


    @GetMapping("/addParticipant")
    String page2(){
    return "addParticipant";
    }

    @GetMapping("/profile")
    String page3(){
        return "profile";
    }

    @GetMapping("/addCompany")
    String page4(){
        return "addCompany";
    }


    @ModelAttribute("allList")
    List<Participant> getAllParticipant(){
      return profilesRepository.findAll();
    }

    @ModelAttribute("allListCompany")
    List<Company> getAllCompany(){
        return companyRepository.findAll();
    }

    @ModelAttribute("participants")
    List<Participant> getOneParticipant(){
        return participants;
    }

    @ModelAttribute("newParticipant")
    Participant getNewParticipant(){
        return new Participant();
    }

    @ModelAttribute("newCompany")
    Company getNewCompany(){
        return new Company();
    }

    @ModelAttribute("loggedUserName")
    String principal(Principal principal) {
        String text = "Welcome";
        if(principal != null){
            text += " " + principal.getName();
        }

        return text;
    }


    @PostMapping("addParticipant")
    String addParticipant(Participant participant){
        profilesRepository.save(participant);
        return "redirect:/";
    }

    @PostMapping("/registration")
    String registraion(){
        return "redirect:/addCompany";
    }

    @PostMapping("/")
    String addParticipants(){
        return "redirect:/addParticipant";
    }

    @PostMapping("addCompany")
    String addCompany(@RequestParam String name, @RequestParam String password,@RequestParam String email, @RequestParam String username){
        Company company = new Company();
        company.setUsername(username);
        company.setEmail(email);
        company.setName(name);
        company.setPassword(passwordEncoder.encode(password));
        company.setAuthorities(Stream.of("USER").collect(Collectors.toSet()));
        companyRepository.save(company);
        return "redirect:/";
    }

    @PostMapping("addAdmin")
    String addAdmin(@RequestParam String name, @RequestParam String password,@RequestParam String email, @RequestParam String username){
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setName(name);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setAuthorities(Stream.of("ADMIN").collect(Collectors.toSet()));
        adminRepository.save(admin);
        return "redirect:allParticipants";
    }


    @PostMapping("like")
    String like(Principal principal, @RequestParam String id1) {
        Optional<Participant> participant = profilesRepository.findById(Long.valueOf(id1));
        if (participant.isPresent()) {
            Optional<Company> CompanyUsername = companyRepository.findOneByUsername(principal.getName());
            if (CompanyUsername.isPresent()) {
                if(participant.get().getCompanyList().contains(CompanyUsername.get())){
                    participant.get().addCompany(CompanyUsername.get());
                    participant.get().removeCompany(CompanyUsername.get());
                }
                else{
                    participant.get().addCompany(CompanyUsername.get());
                }
                //participant.get().setLike(true);
                //participant.get().setDislike(false);
                companyRepository.save(CompanyUsername.get());
            }
        }
        return "redirect:/";
    }


    @DeleteMapping("dislike")
    String dislike(@RequestParam String idCompany , @RequestParam String idParticipant,Principal principal){
        //Optional<Company> company = companyRepository.findById(Long.valueOf(idCompany));
        Optional<Participant> participant = profilesRepository.findById(Long.valueOf(idParticipant));
        Optional<Company> company = companyRepository.findOneByUsername(principal.getName());
        if(company.isPresent()){
            if(participant.get().getCompanyList().contains(company.get())) {
                System.out.println(participant.get().getCompanyList());
                participant.get().removeCompany(company.get());
            }
            //participant.get().setLike(false);
            //participant.get().setDislike(true);
            profilesRepository.save(participant.get());
        }
        return "redirect:/";
    }

    @PostMapping("/profile")
    String goProfile(@RequestParam String idParticipant){
        List<Participant> collect = profilesRepository.findAll().stream()
                .filter(participant -> participant.getId().toString().equalsIgnoreCase(idParticipant))
                .collect(Collectors.toList());
            participants =collect;
        return "redirect:profile";
    }
}

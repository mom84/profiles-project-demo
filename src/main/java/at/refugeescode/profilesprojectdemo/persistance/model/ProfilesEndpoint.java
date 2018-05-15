package at.refugeescode.profilesprojectdemo.persistance.model;
import at.refugeescode.profilesprojectdemo.persistance.repository.AdminRepository;
import at.refugeescode.profilesprojectdemo.persistance.repository.CompanyRepository;
import at.refugeescode.profilesprojectdemo.persistance.repository.ProfilesRepository;
import at.refugeescode.profilesprojectdemo.security.AdminPrincipal;
import at.refugeescode.profilesprojectdemo.security.UserPrincipal;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class ProfilesEndpoint {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    private ProfilesRepository profilesRepository;

    private CompanyRepository companyRepository;

    private AdminRepository adminRepository;

    private EmailService emailService;

    private PasswordEncoder passwordEncoder;

    private List<Participant> participants = new ArrayList<>();

    private Participant participant;


    public ProfilesEndpoint(ProfilesRepository profilesRepository, CompanyRepository companyRepository, AdminRepository adminRepository, EmailService emailService, PasswordEncoder passwordEncoder, Participant participant, List<Participant> participants) {
        this.profilesRepository = profilesRepository;
        this.companyRepository = companyRepository;
        this.adminRepository = adminRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/search")
    String page5(){
        return "search";
    }

    @GetMapping("/edit")
    String page6(){
        return "edit";
    }

    @GetMapping("/demo")
    String page7(){
        return "demo";
    }



    @ModelAttribute("allList")
    List<Participant> getAllParticipant(){
      return profilesRepository.findAll();
    }

    @ModelAttribute("participants")
    List<Participant> getOneParticipant(){
        return participants;
    }


    @ModelAttribute("allListCompany")
    List<Company> getAllCompany(){
        return companyRepository.findAll();
    }


    @ModelAttribute("newParticipant")
    Participant getNewParticipant(){
        return new Participant();
    }

    @ModelAttribute("newCompany")
    Company getNewCompany(){
        return new Company();
    }

    @ModelAttribute("newAdmin")
    Admin getNewAdmin(){
        return new Admin();
    }


    Participant participant1 = new Participant();

    @ModelAttribute("p")
    Participant editParticipant(){
        return  participant1;
    }

//    @ModelAttribute("loggedUserName")
//    String principal(Principal principal) {
//        String text =" ";
//        if(principal != null){
//            text += "Welcome " + principal.getName();
//        }
//        return text;
//    }

    @ModelAttribute("loggedCompanyName")
    String principal(@AuthenticationPrincipal UserPrincipal principal) {
        String text =" ";
        if(principal != null){
            text += "Welcome " + principal.getCompany().getName();
        }
        return text;
    }

    @ModelAttribute("loggedAdminName")
    String principal2(@AuthenticationPrincipal AdminPrincipal principal) {
        String text =" ";
        if(principal != null){
            text += "Welcome " + principal.getAdmin().getName();
        }
        return text;
    }


    @PostMapping("addParticipant")
    String addParticipant(Participant participant, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        if (!file.isEmpty()) {
            try {
                String UPLOADED_FOLDER = "C:\\Users\\Mohammad\\Projects\\profiles-project-demo\\src\\main\\resources\\static\\images";
                byte[] bytes = file.getBytes();
                File serverFile = new File(UPLOADED_FOLDER+File.separator+ file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                participant.setImage(file.getOriginalFilename());
                this.participant=participant;
                profilesRepository.save(this.participant);
                redirectAttributes.addFlashAttribute("flash.message","Successfully uploaded");

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("flash.message","Failed to upload");
                return "You failed to upload " + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + " because the file was empty.";
        }
        return "redirect:/";
    }



    @PostMapping("update")
    String goedit(@RequestParam String id ){
        Optional<Participant> oldParticipant = profilesRepository.findAll().stream()
                .filter(participant1 -> participant1.getId().toString().equalsIgnoreCase(id))
                .findFirst();
        participant1 =oldParticipant.get();
        return "redirect:/edit";
    }

    @PostMapping("/edit")
    public String updateParticipant(@RequestParam String name,@RequestParam String github,@RequestParam String email,@RequestParam String phone , @RequestParam String address , @RequestParam String specialization ,  @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            try {
                String UPLOADED_FOLDER = "C:\\Users\\Mohammad\\Projects\\profiles-project-demo\\src\\main\\resources\\static\\images";
                byte[] bytes = file.getBytes();
                File serverFile = new File(UPLOADED_FOLDER+File.separator+ file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                participant1.setImage(file.getOriginalFilename());
                redirectAttributes.addFlashAttribute("flash.message","Successfully uploaded");

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("flash.message","Failed to upload");
                return "You failed to upload " + " => " + e.getMessage();
            }
        }
//        else {
//            return "You failed to upload " + " because the file was empty.";
//        }
        participant1.setName(name);
        participant1.setAddress(address);
        participant1.setEmail(email);
        participant1.setSpecialization(specialization);
        participant1.setPhone(phone);
        participant1.setGithub(github);
        profilesRepository.save(participant1);
        return "redirect:/";
    }


    @PostMapping("addCompany")
    String addCompany(@Valid Company company , BindingResult bindingResult){
            if (bindingResult.hasErrors()) {
                return page4();
            }
        company.setUsername(company.getUsername());
        company.setEmail(company.getEmail());
        company.setName(company.getName());
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        company.setAuthorities(Stream.of("USER").collect(Collectors.toSet()));
        companyRepository.save(company);
        return "redirect:/";
    }

//    @PostMapping("addAdmin")
//    String addAdmin(@RequestParam String name, @RequestParam String password,@RequestParam String email, @RequestParam String username){
//        Admin admin = new Admin();
//        admin.setUsername(username);
//        admin.setEmail(email);
//        admin.setName(name);
//        admin.setPassword(passwordEncoder.encode(password));
//        admin.setAuthorities(Stream.of("ADMIN").collect(Collectors.toSet()));
//        adminRepository.save(admin);
//        return "redirect:allParticipants";
//    }


    @PostMapping("like")
    String like(Principal principal, @RequestParam String id1) throws MessagingException {
        Optional<Participant> participant = profilesRepository.findById(Long.valueOf(id1));
        if (participant.isPresent()) {
            Optional<Company> CompanyUsername = companyRepository.findOneByUsername(principal.getName());
            if (CompanyUsername.isPresent()) {
                    participant.get().addCompany(CompanyUsername.get());
                participant.get().setLike(true);
                participant.get().setDislike(false);
                log.info("Spring Mail - Sending Email with Inline Attachment Example");

                Mail mail = new Mail();
                mail.setFrom("no-reply@memorynotfound.com");
                mail.setTo("mohammadalmosleh66@gmail.com");
                //mail.setTo(CompanyUsername.get().getEmail());
                mail.setSubject("Sending Email with Inline Attachment Example");
                mail.setContent( CompanyUsername.get().getName()+" is very interested in " + participant.get().getName());
                emailService.sendSimpleMessage(mail);
                companyRepository.save(CompanyUsername.get());
            }
        }
        return "redirect:/";
    }


    @DeleteMapping("dislike")
    String dislike(@RequestParam String idCompany , @RequestParam String idParticipant,Principal principal) throws MessagingException {
        Optional<Participant> participant = profilesRepository.findById(Long.valueOf(idParticipant));
        Optional<Company> company = companyRepository.findOneByUsername(principal.getName());
        if(company.isPresent()){
            if(participant.get().getCompanyList().contains(company.get())) {
                participant.get().removeCompany(company.get());
            }
            Mail mail = new Mail();
            mail.setFrom("no-reply@memorynotfound.com");
            //set the person name that you want to send to.
            mail.setTo("mohammadalmosleh66@gmail.com");
            mail.setSubject("Sending Email with Inline Attachment Example");
            mail.setContent( company.get().getName()+" NOT interested in " + participant.get().getName()+" any more");

            emailService.sendSimpleMessage(mail);
            participant.get().setLike(false);
            participant.get().setDislike(true);
            profilesRepository.save(participant.get());
        }
        return "redirect:/";
    }

    @PostMapping("/profile")
    String goProfile(@RequestParam String idParticipant){
        List<Participant> collect = profilesRepository.findAll().stream()
                .filter(participant -> participant.getId().toString().equalsIgnoreCase(idParticipant))
                .collect(Collectors.toList());
            participants = collect;
        return "redirect:profile";
    }

    @PostMapping("/search")
    String getOneParticipant(String name){
        List<Participant> collect = profilesRepository.findAll().stream()
                .filter(patient1 -> patient1.getSpecialization().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if(collect.isEmpty()){
            List<Participant> no_participant_like_this_name = Stream.of(new Participant("No Participant Like This Name"))
                    .collect(Collectors.toList());
            participants =no_participant_like_this_name;
        }
        else {
            participants = collect;
        }
        return "redirect:/search";
    }

    @PostMapping("/delete")
    String deleteToDo(String id1){
        Optional<Participant> participant = profilesRepository.findById(Long.valueOf(id1));
        System.out.println(participant.get());
        if(participant.isPresent()){
            profilesRepository.delete(participant.get());
        }
     return "redirect:/";
    }
}

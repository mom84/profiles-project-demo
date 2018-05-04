package at.refugeescode.profilesprojectdemo.persistance.model;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Component
public class Participant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String specialization;

    @Column(name = "column_name", columnDefinition = "BOOLEAN")
    private Boolean like;

    @Column(name = "column_name2", columnDefinition = "BOOLEAN")
    private Boolean dislike;

    //@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Company> companyList;


    public Participant() {
    }

    public Participant(String name) {
        this.name = name;
    }

    public Boolean getDislike() {
        return dislike;
    }

    public void setDislike(Boolean dislike) {
        this.dislike = dislike;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public Set<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(Set<Company> companyList) {
        this.companyList = companyList;
    }

    public void addCompany(Company company){
        companyList.add(company);
    }

    public void removeCompany(Company company){
        companyList.remove(company);
    }

    public String showCompanyName(){
      return   companyList.stream().map(company -> company.getName())
                .collect(Collectors.joining(", "));
    }

    public String showCompanyId(){
        return   companyList.stream().map(company -> company.getId().toString())
                .collect(Collectors.joining(", "));
    }
}

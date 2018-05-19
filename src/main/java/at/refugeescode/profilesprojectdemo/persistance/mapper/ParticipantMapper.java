package at.refugeescode.profilesprojectdemo.persistance.mapper;

import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    private Long id;

    private String name;

    private String email;

    private String github;

    private String phone;

    private String address;

    private String specialization;

    private  String education;

    private String image;

    private String isLike ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ParticipantMapper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", github='" + github + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", specialization='" + specialization + '\'' +
                ", education='" + education + '\'' +
                ", image='" + image + '\'' +
                ", isLike=" + isLike +
                '}';
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }
}

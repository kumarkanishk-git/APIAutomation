package pojo;

import java.util.List;

public class PostRequestBody {
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private List<String> address;
    private List<CityRequest> city;


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<CityRequest> getCity() {
        return city;
    }

    public void setCity(List<CityRequest> city) {
        this.city = city;
    }
}


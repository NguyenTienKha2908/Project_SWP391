package admin_user.Dto;


import jakarta.validation.constraints.NotNull;

public class RegisterDto {

    private  String email;
    private  String password;

    @NotNull
    private  String role;
    private int status;
    private String fullname;
    private String address;
    private String phone;

    public RegisterDto(String email, String password, String role, int status, String fullname, String address, String phone) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

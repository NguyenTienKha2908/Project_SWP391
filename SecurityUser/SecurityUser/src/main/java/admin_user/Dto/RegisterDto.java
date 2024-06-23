package admin_user.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {

    private  String email;
    private  String password;

    @NotNull
    private  int role;
    private boolean status;
    private String fullname;
    private String address;
    private String phone;

    public RegisterDto(String email, String password, int role, boolean status, String fullname, String address, String phone) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

}

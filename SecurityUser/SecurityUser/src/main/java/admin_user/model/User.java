package admin_user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "[Users]")  // Chỉ định tên bảng trong cơ sở dữ liệu
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Thiết lập UserId là khóa chính và tự động tăng
    @Column(name = "User_Id")
    private Integer userId;

    @Column(nullable = false, unique = true,name = "Email")// Email không được null và phải là duy nhất
    private String email;

    @Column(nullable = false,name = "Password")  // Mật khẩu không được null
    private String password;

    //@Column(nullable = false)  // Vai trò không được null
    @Column(name = "Role_Id")
    private int role;

    @Column(nullable = false,name = "Status")  // Trạng thái không được null
    private int status;

    public User(String email, String password, int role, int status) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;

    }

}

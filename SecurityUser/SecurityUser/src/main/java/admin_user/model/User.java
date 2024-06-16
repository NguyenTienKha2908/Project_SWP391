package admin_user.model;

import jakarta.persistence.*;

@Entity
@Table(name = "[User]")  // Chỉ định tên bảng trong cơ sở dữ liệu
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Thiết lập UserId là khóa chính và tự động tăng
    private Integer userId;

    @Column(nullable = false, unique = true)  // Email không được null và phải là duy nhất
    private String email;

    @Column(nullable = false)  // Mật khẩu không được null
    private String password;

    //@Column(nullable = false)  // Vai trò không được null
    private String role;

    @Column(nullable = false)  // Trạng thái không được null
    private int status;

    public User() {
        super();
    }

    public User(String email, String password, String role, int status) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;

    }

    // Getter và setter cho userId
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter và setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter và setter cho password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter và setter cho role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getter và setter cho status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}

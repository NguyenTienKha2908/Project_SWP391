package admin_user.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "[Customer]")  // Chỉ định tên bảng trong cơ sở dữ liệu
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Customer_Id")// Thiết lập UserId là khóa chính và tự động tăng
    private Integer customerId;

    @Column(name = "User_Id")
    private Integer userId;  // Thiết lập UserId là khóa chính và là khóa ngoại


    @Column(nullable = false, name = "fullname")  // Tên đầy đủ không được null
    private String fullname;

    @Column(nullable = false,name = "address")  // Địa chỉ không được null
    private String address;

    @Column(nullable = false, name ="phone")  // Số điện thoại không được null
    private String phone;

//    @OneToOne
//    @MapsId  // Thiết lập quan hệ một-một với User và ánh xạ Id
//    @JoinColumn(name = "user_id")  // Chỉ định cột khóa ngoại
//    private User user;

    public Customer() {}

    public Customer(Integer userId, String fullname, String address, String phone) {
        this.userId = userId;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

    // Getters and setters

    // Getter và setter cho userId
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter và setter cho fullname
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // Getter và setter cho address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter và setter cho phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


}

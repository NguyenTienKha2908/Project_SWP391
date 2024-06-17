package admin_user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "[Customer]")  // Chỉ định tên bảng trong cơ sở dữ liệu
public class Customer {

    @Id
    @GeneratedValue(generator = "customer-id-generator")
    @GenericGenerator(name = "customer-id-generator", strategy = "admin_user.util.CustomerIdGenerator")
    @Column(name = "Customer_Id", length = 10)
    private String customerId;

    @Column(name = "User_Id")
    private Integer userId;  // Thiết lập UserId là khóa chính và là khóa ngoại


    @Column(nullable = false, name = "Full_Name")  // Tên đầy đủ không được null
    private String fullname;

    @Column(nullable = false,name = "Address")  // Địa chỉ không được null
    private String address;

    @Column(nullable = false, name ="Phone_Number")  // Số điện thoại không được null
    private String phone;


    public Customer() {}

    public Customer(Integer userId, String fullname, String address, String phone) {
        this.userId = userId;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

}

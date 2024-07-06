package swp391.showblog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "Employee_Id")
    private String employeeId;
    @Column(name = "Full_Name")
    private String fullName;
    @Column(name = "User_Id")
    private int userId;

    @OneToMany(mappedBy = "employee")
    private List<Blog> blogs;
}

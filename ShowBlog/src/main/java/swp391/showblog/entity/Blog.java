package swp391.showblog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Blogs")
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @Column(name = "Blog_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogId;
    @Column(name = "Title")
    private String title;
    @Column(name = "[Content]")
    private String content;
    @Column(name = "Date")
    private Date date;

    @Column(name = "status")
    private boolean status;
    @Column(name = "Img_Url")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "Employee_Id", referencedColumnName = "Employee_Id")
    private Employee employee;


}

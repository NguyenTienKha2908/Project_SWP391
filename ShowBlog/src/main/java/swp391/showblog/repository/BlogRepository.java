package swp391.showblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp391.showblog.entity.Blog;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findByStatus(Boolean status);
}

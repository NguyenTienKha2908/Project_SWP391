package swp391.showblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp391.showblog.entity.Blog;
import swp391.showblog.repository.BlogRepository;

import java.util.List;

@Service
public class BlogService{

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findByStatus(true);
    }

    public Blog getBlogById(int blogId) {
        return blogRepository.findById(blogId).orElse(null);
    }

}

package com.connectsphere.repo;
import com.connectsphere.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface PostRepository extends JpaRepository<Post,Long> {
 List<Post> findAllByOrderByCreatedAtDesc();
 List<Post> findByAuthorOrderByCreatedAtDesc(AppUser author);
}
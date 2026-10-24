package com.connectsphere.repo;
import com.connectsphere.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CommentRepository extends JpaRepository<PostComment,Long> {
 List<PostComment> findByPostOrderByCreatedAtAsc(Post post);
 long countByPost(Post post);
}
package com.connectsphere.repo;
import com.connectsphere.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LikeRepository extends JpaRepository<PostLike,Long> {
 long countByPost(Post post);
 boolean existsByUserAndPost(AppUser user,Post post);
 void deleteByUserAndPost(AppUser user,Post post);
}
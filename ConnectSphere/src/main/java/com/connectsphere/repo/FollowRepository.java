package com.connectsphere.repo;
import com.connectsphere.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FollowRepository extends JpaRepository<Follow,Long> {
 boolean existsByFollowerAndFollowing(AppUser follower,AppUser following);
 long countByFollowing(AppUser following);
 long countByFollower(AppUser follower);
 void deleteByFollowerAndFollowing(AppUser follower,AppUser following);
}
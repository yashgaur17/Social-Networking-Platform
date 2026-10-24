package com.connectsphere.repo;
import com.connectsphere.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepository extends JpaRepository<AppUser,Long> {
 Optional<AppUser> findByUsername(String username);
 Optional<AppUser> findByEmail(String email);
 boolean existsByUsername(String username);
 boolean existsByEmail(String email);
 List<AppUser> findTop8ByOrderByJoinedAtDesc();
}
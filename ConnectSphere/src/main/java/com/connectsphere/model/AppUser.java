package com.connectsphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="app_users")
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String username;
    @Column(nullable=false) private String displayName;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String passwordHash;
    @Column(length=500) private String bio = "";
    private String avatarUrl = "";
    private LocalDateTime joinedAt = LocalDateTime.now();

    public AppUser() {}
    public AppUser(String username, String displayName, String email, String passwordHash) {
        this.username=username; this.displayName=displayName; this.email=email; this.passwordHash=passwordHash;
    }
    public Long getId(){return id;} public String getUsername(){return username;}
    public String getDisplayName(){return displayName;} public String getEmail(){return email;}
    public String getPasswordHash(){return passwordHash;} public String getBio(){return bio;}
    public String getAvatarUrl(){return avatarUrl;} public LocalDateTime getJoinedAt(){return joinedAt;}
    public void setDisplayName(String v){displayName=v;} public void setBio(String v){bio=v;}
    public void setAvatarUrl(String v){avatarUrl=v;}
}
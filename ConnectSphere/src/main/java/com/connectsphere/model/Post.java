package com.connectsphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private AppUser author;
    @Column(nullable=false, length=2000) private String content;
    private String imageUrl = "";
    private LocalDateTime createdAt = LocalDateTime.now();
    public Post() {}
    public Post(AppUser author,String content,String imageUrl){this.author=author;this.content=content;this.imageUrl=imageUrl==null?"":imageUrl;}
    public Long getId(){return id;} public AppUser getAuthor(){return author;}
    public String getContent(){return content;} public String getImageUrl(){return imageUrl;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
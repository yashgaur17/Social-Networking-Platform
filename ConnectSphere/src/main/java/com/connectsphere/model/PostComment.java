package com.connectsphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class PostComment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private AppUser author;
    @ManyToOne(optional=false) private Post post;
    @Column(nullable=false,length=1000) private String content;
    private LocalDateTime createdAt=LocalDateTime.now();
    public PostComment() {}
    public PostComment(AppUser author,Post post,String content){this.author=author;this.post=post;this.content=content;}
    public Long getId(){return id;} public AppUser getAuthor(){return author;}
    public Post getPost(){return post;} public String getContent(){return content;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
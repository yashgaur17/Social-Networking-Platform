package com.connectsphere.model;

import jakarta.persistence.*;
@Entity
@Table(name="post_likes", uniqueConstraints=@UniqueConstraint(columnNames={"user_id","post_id"}))
public class PostLike {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private AppUser user;
    @ManyToOne(optional=false) private Post post;
    public PostLike() {}
    public PostLike(AppUser user,Post post){this.user=user;this.post=post;}
    public Long getId(){return id;}
}
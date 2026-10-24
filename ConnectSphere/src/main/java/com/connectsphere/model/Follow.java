package com.connectsphere.model;

import jakarta.persistence.*;
@Entity
@Table(name="follows", uniqueConstraints=@UniqueConstraint(columnNames={"follower_id","following_id"}))
public class Follow {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private AppUser follower;
    @ManyToOne(optional=false) private AppUser following;
    public Follow() {}
    public Follow(AppUser follower,AppUser following){this.follower=follower;this.following=following;}
}
package com.connectsphere.web;

import com.connectsphere.model.*;
import com.connectsphere.repo.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;

@Controller
public class SocialController {
    private final UserRepository users; private final PostRepository posts; private final LikeRepository likes;
    private final CommentRepository comments; private final FollowRepository follows;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public SocialController(UserRepository users,PostRepository posts,LikeRepository likes,CommentRepository comments,FollowRepository follows) {
        this.users=users; this.posts=posts; this.likes=likes; this.comments=comments; this.follows=follows;
    }
    private AppUser current(HttpSession s){Object id=s.getAttribute("userId"); return id==null?null:users.findById((Long)id).orElse(null);}
    private String requireLogin(HttpSession s){return current(s)==null?"redirect:/login":null;}

    @GetMapping("/") public String home(HttpSession session,Model model) {
        AppUser me=current(session); if(me==null)return "redirect:/login";
        model.addAttribute("me",me); model.addAttribute("posts",posts.findAllByOrderByCreatedAtDesc());
        model.addAttribute("users",users.findTop8ByOrderByJoinedAtDesc());
        model.addAttribute("likes",likes); model.addAttribute("comments",comments); model.addAttribute("follows",follows);
        return "feed";
    }
    @GetMapping("/login") public String login(){return "login";}
    @PostMapping("/login") public String doLogin(@RequestParam String username,@RequestParam String password,HttpSession session,RedirectAttributes ra){
        AppUser u=users.findByUsername(username.trim()).orElse(null);
        if(u==null||!encoder.matches(password,u.getPasswordHash())){ra.addFlashAttribute("error","Username or password is incorrect.");return "redirect:/login";}
        session.setAttribute("userId",u.getId()); return "redirect:/";
    }
    @GetMapping("/register") public String register(){return "register";}
    @PostMapping("/register") public String doRegister(@RequestParam String username,@RequestParam String displayName,@RequestParam String email,@RequestParam String password,RedirectAttributes ra,HttpSession session){
        username=username.trim().toLowerCase(); email=email.trim().toLowerCase();
        if(username.length()<3||username.length()>24||!username.matches("[a-z0-9_.]+")){ra.addFlashAttribute("error","Username must be 3–24 characters: letters, numbers, _ or .");return "redirect:/register";}
        if(password.length()<8){ra.addFlashAttribute("error","Use a password with at least 8 characters.");return "redirect:/register";}
        if(users.existsByUsername(username)||users.existsByEmail(email)){ra.addFlashAttribute("error","That username or email is already registered.");return "redirect:/register";}
        AppUser u=users.save(new AppUser(username,displayName.trim(),email,encoder.encode(password)));
        session.setAttribute("userId",u.getId());return "redirect:/";
    }
    @PostMapping("/logout") public String logout(HttpSession s){s.invalidate();return "redirect:/login";}
    @PostMapping("/posts") public String createPost(@RequestParam String content,@RequestParam(required=false) String imageUrl,HttpSession s,RedirectAttributes ra){
        AppUser me=current(s);if(me==null)return "redirect:/login";
        String body=content==null?"":content.trim();if(body.isBlank()||body.length()>2000){ra.addFlashAttribute("error","Post must contain 1–2000 characters.");return "redirect:/";}
        posts.save(new Post(me,body,imageUrl));return "redirect:/";
    }
    @PostMapping("/posts/{id}/like") public String toggleLike(@PathVariable Long id,HttpSession s){
        AppUser me=current(s);if(me==null)return "redirect:/login";
        posts.findById(id).ifPresent(p->{if(likes.existsByUserAndPost(me,p))likes.deleteByUserAndPost(me,p);else likes.save(new PostLike(me,p));});
        return "redirect:/";
    }
    @PostMapping("/posts/{id}/comments") public String comment(@PathVariable Long id,@RequestParam String content,HttpSession s,RedirectAttributes ra){
        AppUser me=current(s);if(me==null)return "redirect:/login";
        Post p=posts.findById(id).orElse(null);String body=content==null?"":content.trim();
        if(p!=null&&!body.isBlank()&&body.length()<=1000)comments.save(new PostComment(me,p,body));
        else ra.addFlashAttribute("error","Comment must contain 1–1000 characters.");
        return "redirect:/";
    }
    @PostMapping("/users/{id}/follow") public String follow(@PathVariable Long id,HttpSession s){
        AppUser me=current(s);if(me==null)return "redirect:/login";
        AppUser other=users.findById(id).orElse(null);
        if(other!=null&&!other.getId().equals(me.getId())){if(follows.existsByFollowerAndFollowing(me,other))follows.deleteByFollowerAndFollowing(me,other);else follows.save(new Follow(me,other));}
        return "redirect:/";
    }
    @GetMapping("/profile/{username}") public String profile(@PathVariable String username,HttpSession session,Model model){
        AppUser me=current(session);if(me==null)return "redirect:/login";
        AppUser target=users.findByUsername(username).orElse(null);if(target==null)return "redirect:/";
        model.addAttribute("me",me);model.addAttribute("profile",target);model.addAttribute("posts",posts.findByAuthorOrderByCreatedAtDesc(target));
        model.addAttribute("likes",likes);model.addAttribute("follows",follows);model.addAttribute("comments",comments);
        model.addAttribute("isFollowing",follows.existsByFollowerAndFollowing(me,target));
        return "profile";
    }
    @PostMapping("/profile/update") public String updateProfile(@RequestParam String displayName,@RequestParam(required=false) String bio,@RequestParam(required=false) String avatarUrl,HttpSession s,RedirectAttributes ra){
        AppUser me=current(s);if(me==null)return "redirect:/login";
        if(displayName.trim().isBlank()||displayName.length()>60|| (bio!=null&&bio.length()>500)){ra.addFlashAttribute("error","Check display name and bio length.");return "redirect:/profile/"+me.getUsername();}
        me.setDisplayName(displayName.trim());me.setBio(bio==null?"":bio.trim());me.setAvatarUrl(avatarUrl==null?"":avatarUrl.trim());users.save(me);
        ra.addFlashAttribute("success","Profile updated.");return "redirect:/profile/"+me.getUsername();
    }
}
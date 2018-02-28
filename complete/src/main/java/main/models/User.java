package main.models;

import main.exceptions.NoPostException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User<P> {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "posts")
    @OneToMany(targetEntity = User.class)
    private List<Post<P>> post;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(){}

    public Post<Clothes> getOneClothesPost(int id){
        for(Post clothesPost: post){
            if(clothesPost.getId().equals(id)) return clothesPost;
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post<P>> getPost() {
        return post;
    }

    public void addPost(Post post) {
        this.post.add(post);
    }
}

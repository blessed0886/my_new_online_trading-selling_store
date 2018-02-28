package main.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.awt.*;
import java.util.Collection;

@Entity
public class Clothes implements Post {

    @Column
    @Id
    private Integer id;

    @Column
    private String title;

    @Column
    private String body;

    @Column
    @OneToMany(targetEntity = Clothes.class)
    private Collection<Image> image;

    public Clothes(Integer id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public Collection<Image> getAllImages() {
        return image;
    }

    @Override
    public void addImage(Image image) {
        this.image.add(image);
    }
}

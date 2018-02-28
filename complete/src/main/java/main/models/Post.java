package main.models;

import java.awt.*;
import java.util.Collection;

public interface Post<p> {

    Integer getId();

    void setId(Integer Id);

    String getTitle();

    void setTitle(String title);

    String getBody();

    void setBody(String body);

    Collection<Image> getAllImages();

    void addImage(Image image);
}

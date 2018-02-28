package main.services;

import main.dao.UserRepo;
import main.exceptions.NoPostException;
import main.exceptions.NoUserException;
import main.models.Clothes;
import main.models.Post;
import main.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class ClothesService {

    @Autowired
    UserRepo userRepo;

    private List<Integer> idNumbers;

    public Post<Clothes> addNewClothesPost(String title, String body, String userName) throws Exception{
        User user = checkForUser(userName);
        Post clothes = new Clothes(idGenerator() ,title, body);
        if(clothes.getId().equals(null)) throw new NoPostException();
        user.addPost(clothes);
        return clothes;
    }

    public void addImageToClothesPost(Image image, Integer id, String username)throws Exception{
        User user = checkForUser(username);
        Post<Clothes> post = checkForPost(user, id);
        post.addImage(image);
    }

    public Post<Clothes> getClothesPost(String username, int id) throws Exception{
        User user = checkForUser(username);
        Post<Clothes> clothesPost = checkForPost(user, id);
        return clothesPost;
    }

    public List<Post> getAllClothesPost(String username) throws Exception{
        User user = checkForUser(username);
        return userRepo.getOne(username).getPost();
    }

    public Collection<Image> getAllImages(int id, String username) throws Exception{
        User user = checkForUser(username);
        Post<Clothes> clothesPost = checkForPost(user, id);
        return clothesPost.getAllImages();
    }

    public void deletePost(String username, int id) throws Exception{
        User user = checkForUser(username);
        user.getPost().remove(checkForPost(user, id));
    }

    //All methods below are helper methods

    private Integer idGenerator() throws NullPointerException{
        Random random = new Random();
        int ender = idNumbers.size() + 1;
        do {
            Integer idNumber = random.nextInt(0 - 1000000);
            if (!(idNumbers.contains(idNumber))) {
                idNumbers.add(idNumber);
                return idNumber;
            }
        }while(idNumbers.size() != ender);
        return null;
    }

    private User checkForUser(String username) throws NoUserException {
        if(userRepo.findOne(username).equals(null)) throw new NoUserException();
        return userRepo.getOne(username);
    }

    private Post<Clothes> checkForPost(User user, Integer id) throws Exception{
        if(user.getOneClothesPost(id).equals(null)) throw new NoPostException();
        return user.getOneClothesPost(id);
    }
}

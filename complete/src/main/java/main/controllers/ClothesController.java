package main.controllers;

import main.dao.UserRepo;
import main.models.Clothes;
import main.models.Post;
import main.services.ClothesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/clothes")
public class ClothesController {

    @Autowired
    ClothesService clothesService;

    @Autowired
    UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(ClothesController.class);

    @GetMapping("username")
    public ResponseEntity<List<Post>> getAllClothes(@PathVariable("username") String username){
        logger.info("Getting all of " + userRepo.findOne(username) + " clothes posts.");
        try {
            List<Post> clothesList = clothesService.getAllClothesPost(username);
            return new ResponseEntity<List<Post>>( clothesList, HttpStatus.FOUND);
        } catch (Exception exp){
            return new ResponseEntity(exp.getClass().getName() ,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Post<Clothes>> getOneClothes(@RequestBody String username, Integer id){
        logger.info("Getting one clothes post of " + userRepo.findOne(username) + ".");
        try {
            Post<Clothes> clothesPost = clothesService.getClothesPost(username, id);
            return new ResponseEntity<Post<Clothes>>(clothesPost, HttpStatus.FOUND);
        } catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Post<Clothes>> addClothesPost(@RequestBody String title, String body, String username){
        logger.info("Adding a clothes post to " + userRepo.getOne(username) + ".");
        try{
            Post<Clothes> clothes = clothesService.addNewClothesPost(title, body, username);
            return new ResponseEntity(clothes, HttpStatus.CREATED);
        } catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping("images")
    public ResponseEntity<Collection<Image>> getAllImages(@RequestBody String username, int id){
        logger.info("Getting all clothes images connected to " + userRepo.getOne(username) + ".");
        try{
            Collection<Image> images = clothesService.getAllImages(id, username);
            return new ResponseEntity<Collection<Image>>(images, HttpStatus.FOUND);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity addImage(@RequestBody Image image, int id, String username){
        logger.info("Adding a image to " + userRepo.getOne(username) + " clothes post.");
        try {
            clothesService.addImageToClothesPost(image, id, username);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping
    public ResponseEntity deletePost(@RequestBody String username, int id){
        logger.info("Deleting a clothes post from: " + userRepo.getOne(username) + ".");
        try {
            clothesService.deletePost(username, id);
            return new ResponseEntity(HttpStatus.GONE);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.CONFLICT);
        }
    }
}

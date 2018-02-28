package main.controllers;

import main.models.User;
import main.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        logger.info("Getting all of the users.");
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        System.out.println("username: " + user.getUsername() + "\npassword: " + user.getPassword());
        logger.info("Adding a user to the database.");
        try {
            userService.addUser(user.getUsername(), user.getPassword());
            return new ResponseEntity(user, HttpStatus.CREATED);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<String> userLogin(@RequestBody String username, String password){
        logger.info("Attempting to login: " + username + ".");
        try {
            String user = userService.userLogin(username, password);
            return new ResponseEntity(user, HttpStatus.ACCEPTED);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody String username){
        logger.info("Attempting to delete: " + username + ".");
        try {
            userService.deleteUser(username);
            return new ResponseEntity(HttpStatus.GONE);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.CONFLICT);
        }
    }

    @PatchMapping
    public ResponseEntity<String> changeUserName(@RequestBody String username, String newUsername){
        logger.info("Changing the user of " + username + " to " + newUsername + ".");
        try{
            String user = userService.changeUserName(username, newUsername);
            return new ResponseEntity(user, HttpStatus.ACCEPTED);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity changeUserPassword(@RequestBody String username, String password){
        logger.info("Changing user password.");
        try{
           userService.changeUserPassword(username, password);
           return new ResponseEntity(HttpStatus.ACCEPTED);
        }catch(Exception exp){
            return new ResponseEntity(exp.getClass().getName(), HttpStatus.CONFLICT);
        }
    }
}

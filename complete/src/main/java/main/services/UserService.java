package main.services;

import main.dao.UserRepo;
import main.exceptions.BadUserNameException;
import main.exceptions.IncorrectUserPassword;
import main.exceptions.NoUserException;
import main.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public User addUser(String username, String password) throws Exception{
        if(checkForUserName(username)) throw new BadUserNameException();
        return userRepo.save(new User(username, password));
    }

    public String userLogin(String username, String password) throws Exception{
        checkForUserName(username);
        if(userRepo.getOne(username).getPassword().equals(password)) return username;
        throw new IncorrectUserPassword();
    }

    public void deleteUser(String username) throws Exception{
        checkForUserName(username);
        userRepo.delete(username);
    }

    public String changeUserName(String username, String newUsername) throws Exception{
        checkForUserName(username);
        checkForUserName(newUsername);
        userRepo.getOne(username).setUsername(newUsername);
        return newUsername;
    }

    public void changeUserPassword(String username, String newPassword) throws Exception{
        checkForUserName(username);
        userRepo.getOne(username).setPassword(newPassword);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    //Helper Methods Below

    private boolean checkForUserName(String username) throws Exception{
        if(userRepo.exists(username)) return true;
        if(username.length() > 20 || username.length() < 8) throw new BadUserNameException();
        System.out.println(username);
        System.out.println("Is this my problem?");
        throw new NoUserException();
    }
}

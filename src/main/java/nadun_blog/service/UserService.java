package nadun_blog.service;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nadun_blog.DTO.UserDTO;
import nadun_blog.model.User;
import nadun_blog.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * This method retrieves all users from the database and maps them to a
     * list of UserDTO objects.
     * 
     * @return List<UserDTO> | null
     * @author nadun
     */
    public List<UserDTO> getUsers() {
        try {
            List<User> userList = userRepo.findAll();
            Type userType = new TypeToken<List<User>>() {
            }.getType();
            return modelMapper.map(userList, userType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save a new user to the database.
     * 
     * @param id
     * @param username
     * @param password
     * @param email
     * @return UserDTO
     * @author nadun
     */
    public UserDTO addUser(UserDTO userDTO) {
        try {
            User user = new User(null, userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), false, null,
                    new Timestamp(System.currentTimeMillis()),
                    null);
            return modelMapper.map(userRepo.save(user), UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

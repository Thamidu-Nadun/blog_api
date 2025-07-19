package nadun_blog.service;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nadun_blog.DTO.UserDTO;
import nadun_blog.model.Role;
import nadun_blog.model.User;
import nadun_blog.repo.UserRepo;
import nadun_blog.util.SecureUtils;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleService roleService;

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
        Role defaultRole = roleService.getDefaultRole();
        if (defaultRole == null) {
            throw new RuntimeException("Default role not found. Please create a default role first.");
        }
        try {
            String token = SecureUtils.generateVerificationToken(userDTO.getEmail());
            User user = new User(
                    null, // UUID (generated)
                    userDTO.getUsername(), // username
                    userDTO.getPassword(), // password (hash in real app)
                    userDTO.getEmail(), // email
                    false, // isVerified
                    token, // verificationCode
                    new Timestamp(System.currentTimeMillis()), // createdAt
                    defaultRole // role
            );
            return modelMapper.map(userRepo.save(user), UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method updates an existing user in the database.
     * 
     * @param userId
     * @param userDTO
     * @return UserDTO | null
     * @author nadun
     */
    public UserDTO updateUser(UUID userId, UserDTO userDTO) {
        try {
            User user = userRepo.findById(userId).orElse(null);
            if (user != null) {
                user.setUsername(userDTO.getUsername() != null ? userDTO.getUsername() : user.getUsername());
                user.setPassword(userDTO.getPassword() != null ? userDTO.getPassword() : user.getPassword());
                user.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : user.getEmail());
                return modelMapper.map(userRepo.save(user), UserDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method deletes a user from the database by its ID.
     * 
     * @param id
     * @return UserDTO | null
     * @author nadun
     */
    public UserDTO deleteUser(UUID id) {
        try {
            User user = userRepo.findById(id).orElse(null);
            if (user != null) {
                userRepo.delete(user);
                return modelMapper.map(user, UserDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verify the user
     * 
     * @param id
     * @param verificationCode
     * @return boolean
     */
    public boolean verifyUser(UUID id, String verificationCode) {
        try {
            User user = userRepo.findById(id).orElse(null);
            if (user != null) {
                String userToken = user.getVerificationCode();
                if (userToken != null) {
                    if (userToken.equals(verificationCode)) {
                        user.setVerificationCode(null);
                        user.setVerified(true);
                        userRepo.save(user);
                        return true;
                    } else { // Not Valid Token
                        return false;
                    }
                } else { // No Token Found
                    return false;
                }
            } else { // User Not Found
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

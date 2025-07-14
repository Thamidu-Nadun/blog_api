package nadun_blog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nadun_blog.DTO.Response;
import nadun_blog.DTO.UserDTO;
import nadun_blog.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getusers")
    public ResponseEntity<Response> getUsers() {
        Response response = new Response();
        try {
            List<UserDTO> userList = userService.getUsers();
            response.setResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/adduser")
    public ResponseEntity<Response> addUser(@RequestBody UserDTO userDTO) {
        Response response = new Response();
        try {
            UserDTO user = userService.addUser(userDTO);
            if (user != null) {
                response.setResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), user);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateuser/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        Response response = new Response();
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            if (updatedUser != null) {
                response.setResponse(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), updatedUser);
                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable UUID id) {
        Response response = new Response();
        try {
            UserDTO deletedUser = userService.deleteUser(id);
            if (deletedUser != null) {
                response.setResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(),
                        deletedUser);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

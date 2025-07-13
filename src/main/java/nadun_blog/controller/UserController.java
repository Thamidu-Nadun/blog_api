package nadun_blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nadun_blog.DTO.Response;
import nadun_blog.DTO.UserDTO;
import nadun_blog.service.UserService;
import nadun_blog.util.ResponseCode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            response.setResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), userList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponse(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/adduser")
    public ResponseEntity<Response> addUser(@RequestBody UserDTO userDTO) {
        Response response = new Response();
        try {
            UserDTO user = userService.addUser(userDTO);
            response.setResponse(ResponseCode.CREATED.getCode(), ResponseCode.CREATED.getMessage(), user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setResponse(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

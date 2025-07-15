package nadun_blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nadun_blog.DTO.Response;
import nadun_blog.DTO.RoleDTO;
import nadun_blog.service.RoleService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/getroles")
    public ResponseEntity<Response> getRoles() {
        Response response = new Response();
        try {
            List<RoleDTO> roleList = roleService.getRoles();
            if (roleList != null && !roleList.isEmpty()) {
                response.setResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), roleList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), "No roles found", new RoleDTO[0]);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), new RoleDTO[0]);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saverole")
    public ResponseEntity<Response> saveRole(@RequestBody RoleDTO roleDTO) {
        Response response = new Response();
        try {
            RoleDTO savedRole = roleService.saveRole(roleDTO);
            if (savedRole != null) {
                response.setResponse(HttpStatus.CREATED.value(), "Role created successfully", savedRole);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setResponse(HttpStatus.BAD_REQUEST.value(), "Failed to create role", roleDTO);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), roleDTO);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updaterole/{id}")
    public ResponseEntity<Response> updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        Response response = new Response();
        try {
            RoleDTO updatedRole = roleService.updateRole(id, roleDTO);
            if (updatedRole != null) {
                response.setResponse(HttpStatus.OK.value(), "Role updated successfully", updatedRole);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponse(HttpStatus.BAD_REQUEST.value(), "Failed to update role", roleDTO);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), roleDTO);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleterole/{id}")
    public ResponseEntity<Response> deleteRole(@PathVariable Integer id) {
        Response response = new Response();
        try {
            boolean isDeleted = roleService.deleteRole(id);
            if (isDeleted) {
                response.setResponse(HttpStatus.OK.value(), "Role deleted successfully", null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), "Role not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package nadun_blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nadun_blog.DTO.PermissionDTO;
import nadun_blog.DTO.Response;
import nadun_blog.service.PermissionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/getpermissions")
    public ResponseEntity<Response> getPermissions() {
        Response response = new Response();
        try {
            List<PermissionDTO> permission_list = permissionService.getPermissions();
            if (permission_list != null && !permission_list.isEmpty()) {
                response.setResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), permission_list);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), "No permissions found", new PermissionDTO[0]);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), new PermissionDTO[0]);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/savepermission")
    public ResponseEntity<Response> savePermission(@RequestBody PermissionDTO permissionDTO) {
        Response response = new Response();
        try {
            PermissionDTO savedPermission = permissionService.savePermission(permissionDTO);
            if (savedPermission != null) {
                response.setResponse(HttpStatus.CREATED.value(), "Permission created successfully", savedPermission);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setResponse(HttpStatus.BAD_REQUEST.value(), "Failed to create permission", permissionDTO);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatepermission/{id}")
    public ResponseEntity<Response> updatePermission(@RequestParam Long id, @RequestBody PermissionDTO permissionDTO) {
        Response response = new Response();
        try {
            PermissionDTO updatedPermission = permissionService.updatePermission(id, permissionDTO);
            if (updatedPermission != null) {
                response.setResponse(HttpStatus.OK.value(), "Permission updated successfully", updatedPermission);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), "Permission not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletepermission/{id}")
    public ResponseEntity<Response> deletePermission(@RequestParam Long id) {
        Response response = new Response();
        try {
            PermissionDTO deleted_permission = permissionService.deletePermission(id);
            if (deleted_permission != null) {
                response.setResponse(HttpStatus.OK.value(), "Permission deleted successfully", deleted_permission);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), "Permission not found", null);
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

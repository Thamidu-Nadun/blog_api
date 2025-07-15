package nadun_blog.service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nadun_blog.DTO.PermissionDTO;
import nadun_blog.model.Permissions;
import nadun_blog.repo.PermissionRepo;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all permissions from the database.
     * 
     * @return
     */
    public List<PermissionDTO> getPermissions() {
        try {
            List<Permissions> permission_list = permissionRepo.findAll();
            Type permissionType = new TypeToken<List<Permissions>>() {
            }.getType();
            return modelMapper.map(permission_list, permissionType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a permission by its ID.
     * 
     * @param id
     * @return
     */
    public Permissions getPermissionById(int id) {
        try {
            return permissionRepo.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save a new permission to the database.
     * 
     * @param permissionDTO
     * @return
     */
    public PermissionDTO savePermission(PermissionDTO permissionDTO) {
        Permissions permission = new Permissions(null, permissionDTO.getName() != null ? permissionDTO.getName() : "");
        return modelMapper.map(permissionRepo.save(permission), PermissionDTO.class);
    }

    /**
     * Update an existing permission.
     * 
     * @param id
     * @param permissionDTO
     * @return
     */
    public PermissionDTO updatePermission(int id, PermissionDTO permissionDTO) {
        try {
            Permissions existingPermission = permissionRepo.findById(id).orElse(null);
            if (existingPermission != null) {
                existingPermission.setName(
                        permissionDTO.getName() != null ? permissionDTO.getName() : existingPermission.getName());
                return modelMapper.map(permissionRepo.save(existingPermission), PermissionDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a permission by its ID.
     * 
     * @param id
     * @return
     */
    public PermissionDTO deletePermission(int id) {
        try {
            Permissions permission = permissionRepo.findById(id).orElse(null);
            if (permission != null) {
                permissionRepo.delete(permission);
                return modelMapper.map(permission, PermissionDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashSet<Permissions> getDefaultPermissions() {
        HashSet<Permissions> permissions = new HashSet<>();
        permissions.addAll(getUserPermissions());
        return permissions;
    }

    private HashSet<Permissions> getUserPermissions() {
        HashSet<Permissions> userPermissions = new HashSet<>();

        // Post
        Permissions read_post = getOrCreatePermission("read_post");
        userPermissions.add(read_post);

        // Comment
        Permissions create_comment = getOrCreatePermission("create_comment");
        Permissions read_comment = getOrCreatePermission("read_comment");
        Permissions update_comment = getOrCreatePermission("update_comment");
        Permissions delete_comment = getOrCreatePermission("delete_comment");

        userPermissions.addAll(Arrays.asList(create_comment, read_comment, update_comment, delete_comment));

        // Like
        Permissions create_like = getOrCreatePermission("create_like");
        Permissions read_like = getOrCreatePermission("read_like");
        Permissions delete_like = getOrCreatePermission("delete_like");

        userPermissions.addAll(Arrays.asList(create_like, read_like, delete_like));

        return userPermissions;

    }

    private Permissions getOrCreatePermission(String name) {
        return permissionRepo.findByName(name)
                .orElseGet(
                        () -> modelMapper.map(savePermission(new PermissionDTO(null, name)), Permissions.class));
    }

}

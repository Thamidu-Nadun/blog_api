package nadun_blog.service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nadun_blog.DTO.RoleDTO;
import nadun_blog.model.Role;
import nadun_blog.repo.RoleRepo;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all roles from the database.
     * 
     * @return List<RoleDTO> | null
     */
    public List<RoleDTO> getRoles() {
        try {
            List<Role> role_list = roleRepo.findAll();
            Type roleType = new TypeToken<List<Role>>() {
            }.getType();
            return modelMapper.map(role_list, roleType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a role by its ID.
     * 
     * @param id
     * @return RoleDTO | null
     */
    public RoleDTO getRoleById(Long id) {
        try {
            return modelMapper.map(roleRepo.findById(id).orElse(null), RoleDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save a new role to the database.
     * 
     * @param roleDTO
     * @return RoleDTO | null
     */
    public RoleDTO saveRole(RoleDTO roleDTO) {
        try {
            Role role = modelMapper.map(roleDTO, Role.class);
            Role savedRole = roleRepo.save(role);
            return modelMapper.map(savedRole, RoleDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update an existing role.
     * 
     * @param id
     * @param roleDTO
     * @return RoleDTO | null
     */
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        try {
            Role existingRole = roleRepo.findById(id).orElse(null);
            if (existingRole != null) {
                existingRole.setName(roleDTO.getName());
                Role updatedRole = roleRepo.save(existingRole);
                return modelMapper.map(updatedRole, RoleDTO.class);
            } else {
                return null; // Role not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a role by its ID.
     * 
     * @param id
     * @return boolean
     */
    public boolean deleteRole(Long id) {
        try {
            if (roleRepo.existsById(id)) {
                roleRepo.deleteById(id);
                return true; // Deletion successful
            } else {
                return false; // Role not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Deletion failed
        }
    }
}

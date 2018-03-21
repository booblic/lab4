package lab4.library.service;

import lab4.library.repository.RoleRepository;
import lab4.library.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRole(Integer roleId) {
        return roleRepository.getOne(roleId);
    }

    public Role getRoleByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }
}

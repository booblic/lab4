package lab4.library.service;

import lab4.library.repository.RoleRepository;
import lab4.library.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role getRole(Integer roleId) {
        return roleRepository.getOne(roleId);
    }

    @Transactional
    public Role getRoleByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }
}

package lab4.library.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Role implements GrantedAuthority {

    @Id
/*    @SequenceGenerator(name = "role_id_sequence_gen",
            sequenceName="role_id_sequence", initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_sequence_gen")*/
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "role_id_sequence_gen",
            sequenceName="role_id_sequence", initialValue = 1000)
    private Integer roleId;

    private String roleName;

    @Column(unique = true)
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {};

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

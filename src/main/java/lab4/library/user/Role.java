package lab4.library.user;

import lab4.library.ReflectionToString;
import lab4.library.annotation.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

/**
 * The class that represents entity - role
 * @author Кирилл
 * @version 1.0
 */
@Entity
@Table
public class Role implements GrantedAuthority {

    public static final String ROLE_MODERATOR = "ROLE_MODER";

    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMIN";

    public static final String ROLE_USER = "ROLE_USER";

    public static final String ROLE_SALES = "ROLE_SALES";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @ToString
    private String roleName;

    @ToString
    @Column(unique = true)
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId) &&
                Objects.equals(roleName, role.roleName) &&
                Objects.equals(authority, role.authority);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roleId, roleName, authority);
    }

    @Override
    public String toString() {
        return ReflectionToString.reflectionToString(this);
    }
}

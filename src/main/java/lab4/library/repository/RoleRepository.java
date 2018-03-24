package lab4.library.repository;

import lab4.library.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, QueryByExampleExecutor<Role> {

    Role findByAuthority(String authority);
}

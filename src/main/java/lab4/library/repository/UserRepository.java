package lab4.library.repository;

import lab4.library.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {

    User findByUsername(String username);
}

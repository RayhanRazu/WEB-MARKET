package webmarket.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webmarket.Entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
    Boolean existsByUsername(String username);
    void deleteByName(String name);
}

package webmarket.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webmarket.Entities.User;
import webmarket.Repository.UserRepository;
import javax.transaction.Transactional;


@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository repository;

    public void save(User user) {
        repository.save(user);
    }
    public void delete(int id) {
        repository.deleteById(id);
    }
    public Boolean exists(String username){
        return  repository.existsByUsername(username);
    }
    public User getByid(int id){
        return repository.findById(id).orElse(null);
    }
    public User getByUsername(String username){
        return repository.findByUsername(username);
    }
    public Boolean deleteUser( String name){
        User user = getByUsername(name);
        if (user.getRole().equals("USER")){
            repository.deleteByName(name);
            return true;
        }
        else
            return false;
    }

}

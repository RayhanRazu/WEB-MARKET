package webmarket.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webmarket.Entities.Item;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findByGender(String gender);
    List<Item> findByType (String type);
    List<Item> findAllByName(String name);
    List<Item> findByGenderAndType(String gender, String type);
}

package webmarket.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import webmarket.Entities.SoldItem;
import java.util.Date;
import java.util.List;

public interface SoldItemRepository extends CrudRepository<SoldItem, Integer> {

    @Query(value = "select * from sold_items s  where s.id_user = ?1", nativeQuery = true)
    List<SoldItem> findAllByIdUser(int id_user);

    //@Query(value = "select * from sold_items s  where s.id_user = ?1", nativeQuery = true)
    List<SoldItem> findByDateBetween(Date from, Date to);
}

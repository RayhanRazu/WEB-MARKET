package webmarket.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webmarket.Entities.Item;
import webmarket.Entities.StorageItem;

import java.util.List;

@Repository
public interface StorageItemRepository extends CrudRepository<StorageItem, Integer> {


    List<StorageItem> findByItemId(int id);
    StorageItem findBySizeAndItemId(double size, int item_id);
    List<StorageItem> findBySize(double size);
    List<StorageItem> findAllByItemId(int id);
    @Query(value = "select * from storage s  where s.pair_id = ?1", nativeQuery = true)
    StorageItem findByPair__Id(int pair_id);
}

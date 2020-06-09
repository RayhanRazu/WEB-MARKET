package webmarket.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webmarket.Entities.ReservedItem;

import java.util.List;

@Repository
public interface ReservedItemRepository extends CrudRepository<ReservedItem, Integer> {

    void deleteById(int id);
    @Query(value = "select * from reserved_items s  where s.id_user = ?1 and s.storage_pair_id = ?2", nativeQuery = true)
    List<ReservedItem> existsByIdUserAndStoragePairId(int id_user, int storage_pair_id);
    @Query(value = "select * from reserved_items s  where s.id_user = ?1", nativeQuery = true)
    List<ReservedItem> findAllByIdUser(int id_user);

}

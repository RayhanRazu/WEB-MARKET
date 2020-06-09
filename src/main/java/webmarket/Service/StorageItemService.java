package webmarket.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webmarket.Entities.Item;
import webmarket.Entities.StorageItem;
import webmarket.Repository.StorageItemRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class StorageItemService {

    @Autowired
    StorageItemRepository repository;

    public void save(StorageItem item) {
        repository.save(item);
    }

    public List<StorageItem> listAll() {
        return (List<StorageItem>)repository.findAll();
    }

    public List<StorageItem> get(int id) {
        return (List<StorageItem>)repository.findAllById(Collections.singleton(id));
    }

    public boolean deletePair(double size, int id) {
        StorageItem storageItem =  repository.findBySizeAndItemId(size, id);
         if (storageItem.getVolume() > 0){
             storageItem.setVolume(storageItem.getVolume()-1);
             return true;
         }
         else {
             return false;
         }
    }

    public void addPair(double size, int id){
        StorageItem storageItem =  repository.findBySizeAndItemId(size, id);
        storageItem.setVolume(storageItem.getVolume()+1);
    }

    public StorageItem getBySizeAndId(double size, int id){
        return repository.findBySizeAndItemId(size,id);
    }

    public  List<StorageItem> getBySize(double size){
        return  repository.findBySize(size);
    }

    public   List<StorageItem> getByItemId(int id){
        return repository.findByItemId(id);
    }

    public List<StorageItem> getAllByItem_id(int id){
        return repository.findAllByItemId(id);
    }

    public StorageItem getByPair_id(int id){
        return  repository.findByPair__Id(id);
    }

    public Boolean existPair(int id){
        StorageItem storageItem = repository.findByPair__Id(id);
        if (storageItem!= null )
            return true;
        else
            return false;
    }

}

package webmarket.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;
import webmarket.Entities.*;
import webmarket.Repository.ItemRepository;
import webmarket.Repository.ReservedItemRepository;
import webmarket.Repository.SoldItemRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class ItemService {

    ItemRepository repository;
    ReservedItemRepository reservedItemRepository;
    UserService userService;
    StorageItemService storageItemService;
    SoldItemRepository soldItemRepository;

    @Autowired
    public ItemService(ItemRepository repository, ReservedItemRepository reservedItemRepository, UserService userService, StorageItemService storageItemService, SoldItemRepository soldItemRepository) {
        this.repository = repository;
        this.reservedItemRepository = reservedItemRepository;
        this.userService = userService;
        this.storageItemService = storageItemService;
        this.soldItemRepository = soldItemRepository;
    }

    public void save(Item item) {
        repository.save(item);
    }

    public List<Item> listAll() {
        return (List<Item>) repository.findAll();
    }

    public Item get(int id) {
        return repository.findById(id).get();
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Item> findByGender(String gender){
        return (List<Item>)repository.findByGender(gender);
    }

    public List<Item> findByType (String type){
        return (List<Item>)repository.findByType(type);
    }

    public List<Item> findAllByName(String name){
        return (List<Item>)repository.findAllByName(name);
    }

    public void saveReserveItem(int pair_id, int user_id){
        User user = userService.getByid(user_id);
        StorageItem storageItem = storageItemService.getByPair_id(pair_id);
        ReservedItem reservedItem = new ReservedItem(user,storageItem);
        reservedItemRepository.save(reservedItem);
    }

    public void deleteReserveItem(int id){
        reservedItemRepository.deleteById(id);
    }

    public void sellItem(int pair_id, int user_id){
        User user = userService.getByid(user_id);
        StorageItem storageItem = storageItemService.getByPair_id(pair_id);
        SoldItem soldItem = new SoldItem(user,storageItem,storageItem.getItem().getPrice());
        soldItemRepository.save(soldItem);
    }

    public void deleteSoldItem(int id){
        soldItemRepository.deleteById(id);
    }

    public boolean existsItemInReservedRepository(int user_id, int pair_id){
        List<ReservedItem> list =  reservedItemRepository.existsByIdUserAndStoragePairId(user_id, pair_id);
        if (list.size()>0)
            return true;
        else
            return false;
    }

    public List<StorageItem> getUserWishList(int user_id){
        List<ReservedItem> reservedItemList = reservedItemRepository.findAllByIdUser(user_id);
        List<StorageItem> storageItems = new ArrayList<>();
        for (ReservedItem itemR: reservedItemList){
            storageItems.add(itemR.getStorageItem());
        }
        return  storageItems;
    }


    public List<StorageItem> getUserPurchasedlist(int user_id){
        List<SoldItem> reservedItemList = soldItemRepository.findAllByIdUser(user_id);
        List<StorageItem> storageItems = new ArrayList<>();
        for (SoldItem itemR: reservedItemList){
            storageItems.add(itemR.getStorageItem());
        }
        return  storageItems;
    }

    public List<SoldItem> getAllsoldItems(){
        return (List<SoldItem>)soldItemRepository.findAll();
    }

    public List<SoldItem> getsoldItemsInPeriod(Date from, Date to ){
        return soldItemRepository.findByDateBetween(from,to);
    }
}

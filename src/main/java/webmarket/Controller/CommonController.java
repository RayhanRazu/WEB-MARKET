package webmarket.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webmarket.Entities.Item;
import webmarket.Entities.SearchRequest;
import webmarket.Entities.StorageItem;
import webmarket.Service.ItemService;
import webmarket.Service.StorageItemService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/webmarket")
public class CommonController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private StorageItemService storageItemService;


    @GetMapping(path="/catalog")
    public List<Item> getAllItems(Model model) {
        return itemService.listAll();
    }

    @GetMapping(path="/catalog/category")
    public List<Item> getByCategory(@RequestParam String type){
        return itemService.findByType(type);
    }

    @GetMapping(path="/catalog/gender")
    public List<Item> getByGender(@RequestParam String gender){
        return itemService.findByGender(gender);
    }

    @GetMapping(path="/catalog/size")
    public List<StorageItem> getBySize(@RequestParam double size){
        return storageItemService.getBySize(size);
    }


    @GetMapping(path = "/catalog/item")
    public List<StorageItem> getItemWithStorage(@RequestParam int id){
        return  storageItemService.getByItemId(id);
    }


}

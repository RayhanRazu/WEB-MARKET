package webmarket.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webmarket.Entities.Item;
import webmarket.Entities.SoldItem;
import webmarket.Entities.StorageItem;
import webmarket.Entities.User;
import webmarket.Security.AuthRequest;
import webmarket.Security.AuthResponse;
import webmarket.Security.SignUpRequest;
import webmarket.Service.ItemService;
import webmarket.Service.StorageItemService;
import webmarket.Service.UserDetailsServiceImpl;
import webmarket.Service.UserService;
import webmarket.Util.JwtUtil;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/admin")
public class AdminController {

    private ItemService itemService;
    private StorageItemService storageItemService;
    private UserService userService;
    private UserDetailsServiceImpl userDetailsService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AdminController(ItemService itemService, StorageItemService storageItemService, UserService userService, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.itemService = itemService;
        this.storageItemService = storageItemService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/auth")
    public ResponseEntity<?>authAdmin(@RequestBody AuthRequest authRequest){
        UsernamePasswordAuthenticationToken upToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            e.printStackTrace();
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> sugnUpAdmin(@RequestBody SignUpRequest signUpRequest){
        if (signUpRequest.getRole().equals("ADMIN")){

            if (userService.exists(signUpRequest.getUsername())){
                return new ResponseEntity<String>("....Username is already taken!",
                        HttpStatus.BAD_REQUEST);
            }

            User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getRole());
            userService.save(user);
            return ResponseEntity.ok().body("User registered successfully!");
        }

        return new ResponseEntity<String>("User not ADMIN",
                HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path="/item/all")
    public  List<Item> getAllItems(Model model) {
        return itemService.listAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path="/item/add")
    public ResponseEntity<?> addItem ( Model model, @ModelAttribute("item") Item item ) {
        itemService.save(item);
        return ResponseEntity.ok().body("Item added");
    }


    /**
     *
     * Simple html page, form for adding new Item
     *
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path="/item/add")
    public String addItemView (Model model) {
        model.addAttribute("item", new Item());
        return "add_item";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/item/delete")
    public void deleteItem(@RequestParam int id){
        itemService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path="/item/category")
    public List<Item> getByCategory(@RequestParam String type){
        return itemService.findByType(type);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path="/item/gender")
    public List<Item> getByGender(@RequestParam String gender){
        return itemService.findByGender(gender);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/storage/add")
    public ResponseEntity<?> addItemToStorage(@RequestParam int id, @RequestBody StorageItem storage){
        Item item = new Item();
        item.setId(id);
        storage.setItem(item);
        storageItemService.save(storage);
        return ResponseEntity.ok().body("Added new ");
    }

    /**
     *
     * Simple html page, form for adding Item to Storage
     *
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/storage/add")
    public String addItemToStorageView(@RequestParam int id, Model model){
        model.addAttribute("storage", new StorageItem());
        return "add_to_storage";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/storage")
    public List<StorageItem> getItemStorageByID(@RequestParam Integer id){
        return  storageItemService.getByItemId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/storage/all")
    public List<StorageItem> getItemStorageAll(Model model){
        return  storageItemService.listAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/item/sold")
    public List<SoldItem> soldItemList(){
        return  itemService.getAllsoldItems();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/item/sold/period")
    public List<SoldItem> soldItemListByPeriod(@RequestParam
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                           Date from,
                                               @RequestParam
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       Date to ){

        return  itemService.getsoldItemsInPeriod(from,to);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("users/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String name){
        if (userService.deleteUser(name))
            return ResponseEntity.ok().body("User deleted");
        else
            return ResponseEntity.badRequest().body("User is admin. Can not delete");
    }
}

package webmarket.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path="/webmarket/user")
public class UserController {

    private ItemService itemService;
    private StorageItemService storageItemService;
    private UserService userService;
    private UserDetailsServiceImpl userDetailsService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(ItemService itemService, StorageItemService storageItemService, UserService userService, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.itemService = itemService;
        this.storageItemService = storageItemService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authAdmin(@RequestBody AuthRequest authRequest){
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
        if (signUpRequest.getRole().equals("USER")){

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


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveItem(@RequestParam int pair_id, HttpServletRequest req){
        try {
            User user = userService.getByUsername(jwtUtil.getUsernameFromToken(jwtUtil.getToken(req)));
            if (storageItemService.existPair(pair_id))
                itemService.saveReserveItem(pair_id, user.getId());
            else
                return new ResponseEntity<String>("No such pair",
                        HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<String>("You got some problems",
            HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("Item reserved!");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/buy")
    public ResponseEntity<?> buyItem(@RequestParam int pair_id, HttpServletRequest req){
        try {
            User user = userService.getByUsername(jwtUtil.getUsernameFromToken(jwtUtil.getToken(req)));
            if (itemService.existsItemInReservedRepository(user.getId(),pair_id)){
                itemService.sellItem(pair_id, user.getId());
            }else
                return new ResponseEntity<String>("Please,reserve the item, then buy",
                        HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<String>("You got some problems",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("Thank you for shopping with us!");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/wishlist")
    public List<StorageItem> UserWishlist(HttpServletRequest req){
        User user = userService.getByUsername(jwtUtil.getUsernameFromToken(jwtUtil.getToken(req)));
        return itemService.getUserWishList(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/purchased")
    public List<StorageItem> UserPurchasedlist(HttpServletRequest req){
        User user = userService.getByUsername(jwtUtil.getUsernameFromToken(jwtUtil.getToken(req)));
        return itemService.getUserPurchasedlist(user.getId());
    }
}

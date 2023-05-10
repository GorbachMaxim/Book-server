package com.example.bookserver.controller;

import com.example.bookserver.dto.MessageResponse;
import com.example.bookserver.dto.Password;
import com.example.bookserver.dto.SignupRequest;
import com.example.bookserver.dto.pojo.BookDTO;
import com.example.bookserver.dto.pojo.UserDTO;
import com.example.bookserver.model.Book;
import com.example.bookserver.model.ERole;
import com.example.bookserver.model.Role;
import com.example.bookserver.model.User;
import com.example.bookserver.repository.RoleRepository;
import com.example.bookserver.repository.UserRepository;
import com.example.bookserver.service.BookService;
import com.example.bookserver.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")

@CrossOrigin
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookService bookService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable long id){
        User user = userService.getUserById(id);
        user.setReadBooks(null);
        return user;
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User getYourUser(HttpServletRequest request){
        User user = userService.getUserFromJWT(request);
        user.setReadBooks(null);
        user.setPassword(null);
        return user;
    }

    @GetMapping("readbook/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<BookDTO> getReadBooks(@PathVariable long id){
        User user = userService.getUserById(id);
        Set<BookDTO> list = new HashSet<>();
        user.getReadBooks().forEach(book ->{
            list.add(new BookDTO(book));
        });
        return list;
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody @Valid SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDTO){
        User user = userService.getUserById(userDTO.getId());

        if(user == null){
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }


        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User UPDATED"));
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok(new MessageResponse("User DELETED"));
    }

    @PutMapping("/user/password/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@PathVariable long id, @RequestBody @Valid Password password){
        User user = userService.getUserById(id);
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User password CHANGED"));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        for(User u: users)
            u.setReadBooks(null);
        return users;
    }



    @PutMapping("/user/readbook/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addReadBook(HttpServletRequest request, @PathVariable long id){
        User user = userService.getUserFromJWT(request);
        Book book = bookService.getBookById(id);
        user.getReadBooks().add(book);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User read book: " + book.getName()));
    }

    @DeleteMapping("/user/readbook/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteReadBook(HttpServletRequest request, @PathVariable long id){
        User user = userService.getUserFromJWT(request);
        Book book = bookService.getBookById(id);
        user.getReadBooks().remove(book);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User delete read book: " + book.getName()));
    }

//    @PutMapping("/user")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDTO){
//        User user = userService.getUserById(userDTO.getId());
//        user.setUsername(userDTO.getUsername());
//        user.setEmail(userDTO.getEmail());
//
//        if (userDTO.getRoles().size() != 0) {
//            user.setRoles(new HashSet<>());
//            userDTO.getRoles().forEach(r -> {
//                switch (r) {
//                    case "admin":
//                        Role adminRole = roleRepository
//                                .findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
//                        user.getRoles().add(adminRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository
//                                .findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
//                        user.getRoles().add(userRole);
//                        break;
//                }
//            });
//        }
//        userService.saveOrUpdate(user);
//        return ResponseEntity.ok(new MessageResponse("User UPDATED"));
//    }
}

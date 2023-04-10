package com.example.bookserver.service;

import com.example.bookserver.config.jwt.AuthTokenFilter;
import com.example.bookserver.config.jwt.JwtUtils;
import com.example.bookserver.model.User;
import com.example.bookserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void saveOrUpdate(User user){
        userRepository.save(user);
    }

    public void deleteUserById(long id){
        userRepository.deleteById(id);
    }



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s is not found", login)));
        return UserDetailsImpl.build(user);
    }


    public User getUserFromJWT(HttpServletRequest request) throws UsernameNotFoundException{
        String jwt = AuthTokenFilter.parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User is not found")));
        return user;

    }

}

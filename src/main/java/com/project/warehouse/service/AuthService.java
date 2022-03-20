package com.project.warehouse.service;

import com.project.warehouse.entity.AuthToken;
import com.project.warehouse.entity.User;
import com.project.warehouse.repository.AuthTokenRepository;
import com.project.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    final AuthTokenRepository authTokenRepository;
    final UserRepository userRepository;
    final int expire_seconds=3600;

    public Cookie generateToken(User user){
        AuthToken authToken=new AuthToken();
        authToken.setCreated(LocalDateTime.now());
        authToken.setExpireTime(LocalDateTime.now().plusSeconds(expire_seconds));
        authToken.setUser(user);
        AuthToken save = authTokenRepository.save(authToken);
        Cookie cookie=new Cookie("token", save.getId().toString());
        cookie.setMaxAge(expire_seconds);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }
    public User getUser(String phoneNumber, String password){
        return userRepository.findByPhoneNumberAndPasswordAndActiveTrue(phoneNumber, password);
    }
    public boolean checkToken(String token){
        try {
            UUID uuid = UUID.fromString(token);
            Optional<AuthToken> byId = authTokenRepository.findById(uuid);
            if(byId.isEmpty()){
                return false;
            }
            AuthToken authToken = byId.get();
            if(LocalDateTime.now().isAfter(authToken.getExpireTime())){
                authTokenRepository.deleteById(uuid);
                return false;
            }
            return true;
        }catch (Exception e){
            return false;
        }

    }
    public String getToken(Cookie[] cookies){
        if (cookies==null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                return cookie.getValue();
            }
        }
        return "";
    }
    public boolean deleteTokenIf(HttpServletRequest req, HttpServletResponse res){
        if(req.getCookies()==null || !checkToken(getToken(req.getCookies()))){
            Cookie cookie = new Cookie("token", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            res.addCookie(cookie);
            return true;
        }
        return false;
    }
}

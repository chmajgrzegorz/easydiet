package pl.grzegorzchmaj.easydiet.models.services;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.grzegorzchmaj.easydiet.models.entities.User;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
public class UserInfoService {

    private User user;
    private boolean isLogged;
    private UserRepository userRepository;

    @Autowired
    public UserInfoService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(){
        return userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()).get();
    }
}

package pl.grzegorzchmaj.easydiet.services;

import pl.grzegorzchmaj.easydiet.entities.User;

public interface UserService {

    void save (User user);
    User getActualUser();
    void update (User user);
}

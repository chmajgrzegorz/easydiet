package pl.grzegorzchmaj.easydiet.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.grzegorzchmaj.easydiet.entities.Role;
import pl.grzegorzchmaj.easydiet.entities.User;
import pl.grzegorzchmaj.easydiet.exceptions.WebSecurityException;
import pl.grzegorzchmaj.easydiet.repositories.RoleRepository;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testSave() {

        //SETUP
        String password = "password";
        Role role = mock(Role.class);
        User user = new User();
        user.setPassword(password);
        when(roleRepository.findDefaultRoles()).thenReturn(Collections.singleton(role));
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        //CALL
        userService.save(user);

        //VERIFY
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository,times(1)).save(userCaptor.capture());

        final User actualUser = userCaptor.getValue();
        assertEquals("encryptedPassword", actualUser.getPassword());
        assertEquals(Collections.singleton(role),actualUser.getRoles());

    }


    @Test(expected = WebSecurityException.class)
    public void testGetActualUserWhenAuthenticationIsInstanceOfAnonymousAuthenticationToken() {

        //SETUP
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(AnonymousAuthenticationToken.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        //CALL
        userService.getActualUser();
    }

    @Test
    public void testGetActualUserWhenAuthenticationIsNotInstanceOfAnonymousAuthenticationToken() {

        //SETUP
        User user = new User();
        user.setLogin("login");
        user.setAge(12);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(AbstractAuthenticationToken.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByLogin(anyString())).thenReturn(user);
        when(authentication.getName()).thenReturn(user.getLogin());

        //CALL
        User actualUser = userService.getActualUser();

        //VERIFY
        assertEquals(user.getLogin(), actualUser.getLogin());
        assertEquals(user.getAge(), actualUser.getAge());
    }

}
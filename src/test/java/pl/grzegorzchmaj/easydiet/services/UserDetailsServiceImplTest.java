package pl.grzegorzchmaj.easydiet.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.grzegorzchmaj.easydiet.entities.Role;
import pl.grzegorzchmaj.easydiet.entities.User;
import pl.grzegorzchmaj.easydiet.repositories.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    @Mock
    User user;


    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameWhenUsernameNotFound() {

        //CALL
        userDetailsService.loadUserByUsername(null);

    }

    @Test
    public void testLoadUserByUsernameWhenUserIsPresent() {

        //SETUP
        String expectedUsername = "test";
        String expectedPassword = "password";
        Role role = mock(Role.class);
        when(userRepository.findByLogin(anyString())).thenReturn(user);
        when(user.getRoles()).thenReturn(Collections.singleton(role));
        when(role.getName()).thenReturn("user");
        when(user.getPassword()).thenReturn(expectedPassword);
        when(user.getLogin()).thenReturn(expectedUsername);

        //CALL
        UserDetails actualUser = userDetailsService.loadUserByUsername("test");

        //VERIFY
        Set<GrantedAuthority> expectedSet = new HashSet<>();
        expectedSet.add(new SimpleGrantedAuthority("user"));
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertEquals(expectedSet, actualUser.getAuthorities());
    }


}
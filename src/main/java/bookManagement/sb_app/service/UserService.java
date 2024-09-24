package bookManagement.sb_app.service;

import bookManagement.sb_app.Dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public interface UserService {
    public void addUser(UserDto userDto);
    boolean isUsernameUnique(String username);
    public UserDto login(String username, String password);

}

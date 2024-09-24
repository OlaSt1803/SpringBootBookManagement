package bookManagement.sb_app.service.impl;

import bookManagement.sb_app.Dto.UserDto;
import bookManagement.sb_app.entity.User;
import bookManagement.sb_app.repository.UserRepository;
import bookManagement.sb_app.security.SecurityConfig;
import bookManagement.sb_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,SecurityConfig securityConfig, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        if (!isUsernameUnique(userDto.getUsername())) {
            return;
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto.getUsername(), encodedPassword);
        userRepository.save(user);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    @Override
    public UserDto login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return convertToDto(user);
        }
        return null;
    }


}

package com.parquesoft.ecommerce.security;

import com.parquesoft.ecommerce.entity.UserEntity;
import com.parquesoft.ecommerce.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;


@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomerUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + email));

        if(!user.isActive()){
            throw new UsernameNotFoundException("La cuenta de usuario esta inactiva");
        }

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }

}

package com.example.organic.Service;

import com.example.organic.Entity.UsuarioEntity;
import com.example.organic.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convertimos boolean esadmin en rol para Spring Security
        String rol = usuario.EsAdmin() ? "ADMIN" : "USER";

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getPassword(), // aquí va la contraseña tal cual de la DB
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol))
        );
    }
}

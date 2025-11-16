package br.edu.ifce.maracanau.controleacademico.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Value("${security.jwt.roles-claim-name}")
    private String rolesClaimName;

    private final UserDetailsService userDetailsService;

    public JwtTokenConverter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        String username = source.getSubject();
        List<String> roles = Optional.ofNullable(source.getClaimAsStringList(rolesClaimName)).orElse(List.of());
        List<SimpleGrantedAuthority> grantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
    }

}

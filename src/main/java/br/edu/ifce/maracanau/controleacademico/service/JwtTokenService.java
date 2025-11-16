package br.edu.ifce.maracanau.controleacademico.service;

import br.edu.ifce.maracanau.controleacademico.payload.dto.JwtTokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class JwtTokenService {

    @Value("${security.jwt.token.access.expiry-in-seconds}")
    private Integer accessTokenExpiryInSeconds;

    @Value("${security.jwt.token.refresh.expiry-in-seconds}")
    private Integer refreshTokenExpiryInSeconds;

    @Value("${security.jwt.roles-claim-name}")
    private String rolesClaimName;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public JwtTokenDTO accessToken(String login, List<String> roles) {
        Instant createdAt = Instant.now();
        Instant accessExpiresAt = createdAt.plusSeconds(accessTokenExpiryInSeconds);
        Instant refreshExpiresAt = accessExpiresAt.plusSeconds(refreshTokenExpiryInSeconds);
        return new JwtTokenDTO(
                login,
                getAccessToken(login, roles, createdAt, accessExpiresAt),
                getRefreshToken(login, roles, createdAt, refreshExpiresAt),
                LocalDateTime.ofInstant(accessExpiresAt, ZoneId.systemDefault()),
                LocalDateTime.ofInstant(refreshExpiresAt, ZoneId.systemDefault()),
                LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault())
        );
    }

    public JwtTokenDTO refreshToken(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        List<String> roles = Optional.ofNullable(jwt.getClaimAsStringList(rolesClaimName)).orElse(List.of());
        return accessToken(jwt.getSubject(), roles);
    }

    private String getAccessToken(String login, List<String> roles, Instant emitidoEm, Instant expiraEm) {
        String issuer = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .issuer(issuer)
                .subject(login)
                .issuedAt(emitidoEm)
                .expiresAt(expiraEm)
                .claim(rolesClaimName, roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String getRefreshToken(String login, List<String> roles, Instant emitidoEm, Instant expiraEm) {
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .subject(login)
                .issuedAt(emitidoEm)
                .expiresAt(expiraEm)
                .claim(rolesClaimName, roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}

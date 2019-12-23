package ch.alni.userlist.infrastructure.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String CLAIM_ROLES = "roles";
    private final Converter<Jwt, Collection<GrantedAuthority>> standardConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        final ArrayList<GrantedAuthority> result = new ArrayList<>();
        final var authorities = standardConverter.convert(source);
        if (null != authorities) {
            result.addAll(authorities);
        }
        result.addAll(extractRoles(source));
        return result;
    }

    private Collection<? extends GrantedAuthority> extractRoles(Jwt source) {
        return source.getClaimAsStringList(CLAIM_ROLES).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

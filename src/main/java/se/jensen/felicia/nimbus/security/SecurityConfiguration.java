package se.jensen.felicia.nimbus.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import se.jensen.felicia.nimbus.service.MyUserDetailsService;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/swagger-ui.html").permitAll()                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair(
            @Value("${jwt.private-key}") String privateKey,
            @Value("${jwt.public-key}") String publicKey
    ) throws Exception {
        try {
            System.out.println("VÄRDE FRÅN SPRING: " + privateKey);
            // Rensa strängarna från eventuella citattecken och mellanslag
            String cleanPriv = privateKey.replace("\"", "").replaceAll("\\s", "");
            String cleanPub = publicKey.replace("\"", "").replaceAll("\\s", "");

            byte[] privateBytes = Base64.getDecoder().decode(cleanPriv);
            byte[] publicBytes = Base64.getDecoder().decode(cleanPub);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey priv = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
            PublicKey pub = keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));

            return new KeyPair(pub, priv);
        } catch (Exception e) {
            // Här får du reda på exakt vad som händer i loggen
            System.err.println("FEL VID NYCKELSKAPANDE: " + e.getMessage());
            throw e;
        }
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair){
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID("jwt-keyPair.1")
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, Context) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair keyPair){
        return NimbusJwtDecoder
                .withPublicKey((RSAPublicKey) keyPair.getPublic())
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter converter =
                new JwtGrantedAuthoritiesConverter();

        converter.setAuthorityPrefix("");
        converter.setAuthoritiesClaimName("scope");
        JwtAuthenticationConverter authenticationConverter =
                new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return authenticationConverter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationManager(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }
}

package com.matheus.todosimple.config; //Aqui é a pasta que irá criptografar dados, autenticação e será responsável pela segurança em geral.

import com.matheus.todosimple.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//Isso garante que todos os métodos tenham uma segurança global no prepost
public class SecurityConfig {
    //PUBLIC_MATCHERS define a rota pública que não precisará de autenticação

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService getUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    private UserDetailsService userDetailsService;

    private static final String[] PUBLIC_MATCHERS = {"/"};
    //Rotas publicas para o post, precisa estar público para o usuário poder se criar.
    //O /login não é implementado, o próprio Spring security já fica responsável por cria-lo
    private static final String[] PUBLIC_MATCHERS_POST = {"/user", "/login"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //Uma das partes da autenticação (Desenho do diagrama)

        http.cors().and().csrf().disable();//Desabilitar o crsf do cors

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(this.userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
        this.authenticationManager = authenticationManagerBuilder.build();

        http.authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()//Qualquer requisição post q faça match com PUBLIC_MATCHERS será permitido
                .antMatchers(PUBLIC_MATCHERS).permitAll()//tudo que está no PUBLIC_MATCHERS poderá ser acessado com qualquer tipo de requisição (não há nada em "/")
                .anyRequest().authenticated();//Qualquer outro tipo de acesso para qualquer outra rota, precisa de autenticação

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Definir a politica de sessão como STATELESS

        return http.build();//Build é juntar todos os dados e criar o objeto com a instância já pronta.
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {//Serve para criptografar.
        return new BCryptPasswordEncoder();
    }

}
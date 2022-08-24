package com.accountsservices.controller;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.AppUser;
import com.accountsservices.Security.JWTUtils;
import com.accountsservices.Service.AccountService;
import com.accountsservices.dto.AppUserRequestDTO;
import com.accountsservices.dto.AppUserResponseDTO;
import com.accountsservices.dto.AppUserUpdateDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {

    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/adduser")

    public AppUserResponseDTO addUser(@RequestBody AppUserRequestDTO appUser) {
        return accountService.addUser(appUser);
    }

    @PostMapping("/addrole")

    public AppRole addRole(@RequestBody AppRole role) {
        return accountService.addRole(role);
    }

    @PutMapping("/addRoleToUser/{roleName}/{username}")

    public void addRoleToUser(@PathVariable String roleName, @PathVariable String username) {
        accountService.addRoleToUser(roleName, username);
    }

    @GetMapping("/user/{username}")
    public AppUser LoadUserByUsername(@PathVariable String username) {
        return accountService.LoadUserByUsername(username);
    }

    @GetMapping("/users")

    public List<AppUserResponseDTO> listUsers() {
        return accountService.listUsers();
    }
    @PutMapping("/updateUser")

    public void updateUser(@RequestBody AppUserUpdateDTO dto){

        accountService.updateUser(dto);

    }
    @PutMapping("/updateUserConnected")
    public void updateUserConnected(@RequestBody AppUserUpdateDTO dto,Principal principal)
    {
        AppUser user =accountService.LoadUserByUsername(principal.getName());

        accountService.updateUser(dto);
    }

    @GetMapping("/profile")
    public AppUser Profile(Principal principal){
        return accountService.LoadUserByUsername(principal.getName());
    }
    @GetMapping("/refreshtoken")
    public void RefreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String authorizationtoken = request.getHeader(JWTUtils.AUTH_HEADER);
        if (authorizationtoken != null && authorizationtoken.startsWith(JWTUtils.PREFIX)) {
            try {
                String jwt = authorizationtoken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JWTUtils.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser user = accountService.LoadUserByUsername(username);//on peut verifier blacklist apres
                //create token
                String jwtAccesToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() +JWTUtils.EXPIRE_ACCESS))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getAppRoles().stream().map(
                                r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put("Access-token", jwtAccesToken);
                idToken.put("Refresh-token", jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            } catch (Exception e) {
                throw e;
            }

        }else throw new RuntimeException("Refresh token required !!");
    }
}
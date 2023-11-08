package br.senac.pr.exemplospringbootjwt.seguranca.dtos;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private String username;
    private List<String> roles;

    public JwtResponse(String accessToken, String username,  List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }
}

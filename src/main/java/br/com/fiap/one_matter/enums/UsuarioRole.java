package br.com.fiap.one_matter.enums;

import lombok.Getter;

@Getter
public enum UsuarioRole {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getAuthority() {
        return "ROLE_" + this.role.toUpperCase();
    }
}
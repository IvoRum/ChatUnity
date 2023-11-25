package com.dxc.basket.persistence.entities.jpa;

import javax.persistence.Embeddable;

@Embeddable
public class JpaLogin {
    private String username;
    private String password;

    protected JpaLogin() {
        // Needed by JPA.
    }

    @SuppressWarnings("nls")
    public JpaLogin(final String username, final String password) {
        assert username != null : "Username should not be empty!";
        assert password != null : "Password should not be empty!";

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

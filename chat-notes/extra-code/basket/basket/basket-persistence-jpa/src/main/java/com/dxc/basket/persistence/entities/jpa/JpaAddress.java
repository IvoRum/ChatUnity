package com.dxc.basket.persistence.entities.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.dxc.basket.persistence.model.Address;
import com.dxc.basket.persistence.model.Country;

@Entity
public class JpaAddress implements Address, Serializable {

    private static final long serialVersionUID = 139997978500734993L;

    // Composite ID - better use an Embedded ID.

    @Id
    private Country country;

    @Id
    private String city;

    @Id
    private String street;

    @Id
    private String postalCode;

    protected JpaAddress() {
        // Needed by JPA.
    }

    @SuppressWarnings("nls")
    public JpaAddress(final Country country, final String city, final String street, final String postalCode) {
        assert country != null : "Country should not be empty!";
        assert city != null : "City should not be empty!";
        assert street != null : "Street should not be empty!";
        assert postalCode != null : "Postal Code should not be empty!";

        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

}

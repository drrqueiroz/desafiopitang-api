package com.desafiopitang.api.domain;

import com.desafiopitang.api.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Entity(name="USERS")
@Table(name="USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    /**
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param birthday
     * @param login
     * @param password
     * @param phone
     * @param createdAt
     * @param lastLogin
     */
    public User(Long id, String firstName, String lastName, String email, LocalDate birthday, String login, String password, String phone, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Column(name= "EMAIL", unique = true)
    private String email;

    @Column(name="BIRTHDAY", nullable = false)
    private LocalDate birthday;

    @Column(name="LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name= "PASSWORD", nullable = false)
    private String password;

    @Column(name="PHONE", nullable = false)
    private String phone;

    @Column(name="CREATEDAT", nullable = false)
    private LocalDateTime createdAt;

    public void setCreatedAt(LocalDateTime createdAt) {

        if(createdAt != null){
            this.createdAt = LocalDateTime.parse(createdAt.atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    @Column(name="LASTLOGIN")
    private LocalDateTime lastLogin;

   // private UserRole role;


    public void Validator() {

        final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        final Pattern TELEFONE = Pattern.compile("[0-9]*");

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (this.getFirstName() == null || this.getFirstName().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (this.getLastName() == null || this.getLastName().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (this.getEmail() == null || this.getEmail().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (!EMAIL.matcher(this.getEmail()).matches()) {
            throw new BusinessException("Invalid fields", status);
        }
        else if (this.getBirthday() == null) {
            throw new BusinessException("Missing fields", status);
        }
        else if (this.getLogin() == null || this.getLogin().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (this.getPassword() == null || this.getPassword().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (this.getPhone() == null || this.getPhone().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (!TELEFONE.matcher(this.getPhone()).matches()) {
            throw new BusinessException("Invalid fields", status);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return this.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}


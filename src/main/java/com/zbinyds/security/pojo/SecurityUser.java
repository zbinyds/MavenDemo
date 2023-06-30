package com.zbinyds.security.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Package: com.zbinyds.security.pojo
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/29 17:54
 */

@Data
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = -5999164354552749542L;

    private transient User currentUser;

    public SecurityUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUser.getUsername();
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

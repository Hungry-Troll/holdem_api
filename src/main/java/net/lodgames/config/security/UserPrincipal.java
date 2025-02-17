package net.lodgames.config.security;


import net.lodgames.user.constants.LoginType;
import net.lodgames.user.constants.UserStatus;
import net.lodgames.user.model.LoginAddInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;


@Getter
public class UserPrincipal implements UserDetails {

    @Builder
    public UserPrincipal(long userId, String loginId, LoginType loginType, String email, String password, UserStatus status, String userHex, String mobile, Date birthDate, LoginAddInfo loginAddInfo, String nickname) {
        this.userId = userId;
        this.loginId = loginId;
        this.loginType = loginType;
        this.email = email;
        this.password = password;
        this.status = status;
        this.userHex = userHex;
        this.mobile = mobile;
        this.birthDate = birthDate;
        this.loginAddInfo = loginAddInfo;
        this.nickname = nickname;
    }

    private final long userId;
    private final String loginId;
    private final LoginType loginType;
    private final String password;
    private final String email;
    private final String userHex;
    private final UserStatus status;
    private final String mobile;
    private final Date birthDate;
    private final LoginAddInfo loginAddInfo;
    private final String nickname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.status == UserStatus.NORMAL || this.status == UserStatus.LOGOUT;
    }

}



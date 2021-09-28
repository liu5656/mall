package com.macro.mall.portal.domain;

import com.macro.mall.model.UmsMember;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 5:38 下午
 * @desc
 */
public class MemberDetails implements UserDetails {
    private UmsMember member;

    public MemberDetails(UmsMember member) {
        this.member = member;
    }

    public UmsMember getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
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
        return member.getStatus() == 1;
    }
}

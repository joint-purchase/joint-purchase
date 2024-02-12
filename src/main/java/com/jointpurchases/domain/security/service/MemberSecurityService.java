package com.jointpurchases.domain.security.service;

import com.jointpurchases.domain.security.user.User;
import com.jointpurchases.domain.security.user.UserRole;
import com.jointpurchases.domain.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> _siteMember = this.userRepository.findByName(name);
        if (_siteMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        User siteUser = _siteMember.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        // TODO 로직 순서를 바꾸는게 좋을까요?
        if (siteUser.getRole().equals(UserRole.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else if(siteUser.getRole().equals(UserRole.SELLER)){
            authorities.add(new SimpleGrantedAuthority(UserRole.SELLER.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new org.springframework.security.core.userdetails.User(siteUser.getName(), siteUser.getPassword(), authorities);
    }
}

package com.jointpurchases.domain.security.service;

import com.jointpurchases.domain.security.repository.MemberRepository;
import com.jointpurchases.domain.security.MemberRole;
import com.jointpurchases.domain.security.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Member> _siteMember = this.memberRepository.findByName(name);
        if (_siteMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member siteMember = _siteMember.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        // TODO 로직 순서를 바꾸는게 좋을까요?
        if (siteMember.getRole().equals(MemberRole.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else if(siteMember.getRole().equals(MemberRole.SELLER)){
            authorities.add(new SimpleGrantedAuthority(MemberRole.SELLER.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new User(siteMember.getName(), siteMember.getPassword(), authorities);
    }
}

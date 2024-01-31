package com.bopomofo.core.service;

import com.bopomofo.core.entity.po.MemberUser;
import com.bopomofo.memberUser.dao.MemberUserDao;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberUserDao memberUserDao;

    public UserDetailsServiceImpl(MemberUserDao memberUserDao) {
        this.memberUserDao = memberUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberUser> userOptional = memberUserDao.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        MemberUser memberUser = userOptional.get();
        return new User(memberUser.getUsername(),
                memberUser.getPassword(),
                true,
                true,
                true,
                true,
                Collections.emptyList());
    }
}

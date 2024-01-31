package com.bopomofo.memberUser.dao;

import com.bopomofo.core.entity.po.MemberUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberUserDao extends JpaRepository<MemberUser, String>, JpaSpecificationExecutor<MemberUser> {

    Optional<MemberUser> findByUsername(String username);
}

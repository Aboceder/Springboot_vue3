package com.bopomofo.memberUser.controller;

import com.bopomofo.core.entity.po.MemberUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/member")
public class MemberUserController {
    @GetMapping("/getUserInfo")
    public MemberUser getUserInfo() {
        MemberUser memberUser = new MemberUser();
        memberUser.setUsername("bopomofo").setGender("男").setBirthday(LocalDate.parse("1999-09-09"))
                .setPhoneNumber("15566667777").setId("1");
        return memberUser;
    }
}

package com.bopomofo.core.entity.po;

import com.bopomofo.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 会员用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "member_user")
@Where(clause = "if_deleted=0")
public class MemberUser extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(30) COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "VARCHAR(10) COMMENT '性别'")
    private String gender;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '手机号'")
    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(columnDefinition = "date COMMENT '出生年月'")
    private LocalDate birthday;
}


package com.bopomofo.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8595103081593476110L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(columnDefinition = "varchar(32) COMMENT '主键id'")
    protected String id;

    /**
     * 创建人id
     */
    @CreatedBy
    @Column(columnDefinition = "varchar(32) COMMENT '创建人id'")
    protected String creatorUserId;

    /**
     * 创建时间
     */
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(columnDefinition = "datetime(6) COMMENT '创建时间'")
    protected LocalDateTime creatorTime;
    /**
     * 最后修改人
     */
    @LastModifiedBy
    @Column(columnDefinition = "varchar(32) COMMENT '修改人id'")
    protected String lastUpdateUserId;
    /**
     * 最后修改时间
     */
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(columnDefinition = "datetime(6) COMMENT '修改时间'")
    protected LocalDateTime lastUpdateTime;

    /**
     * 是否删除
     */
    @ColumnDefault(value = "false")
    @Column(columnDefinition = "bit(1) COMMENT '是否删除'")
    protected boolean ifDeleted = false;

    /**
     * 排序
     */
    @ColumnDefault(value = "99")
    @Column(columnDefinition = "float(7,2) COMMENT '排序'")
    private float dataSort = 99;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

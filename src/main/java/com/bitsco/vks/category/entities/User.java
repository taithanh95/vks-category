package com.bitsco.vks.category.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = Constant.TABLE.USERS)
public class User extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SEQUENCE.SQ_USERS)
    @SequenceGenerator(name = Constant.SEQUENCE.SQ_USERS, sequenceName = Constant.SEQUENCE.SQ_USERS, allocationSize = 1)
    private Long id;
    @Column(name = "s_username")
    private String username;
    @Column(name = "s_password")
    private String password;
    @Column(name = "s_name")
    private String name;
    @Column(name = "s_email")
    private String email;
    @Column(name = "s_phone")
    private String phone;
    @Column(name = "s_address")
    private String address;
    @Column(name = "n_type")
    private Integer type;
    @Column(name = "n_email_verified")
    private Integer emailVerified;
    @Column(name = "s_image_Url")
    private String imageUrl;

    @Transient
    String typeName;

    @Transient
    @JsonIgnore
    private String passwordDecode;

    public User coppyFrom(User user) {
        if (!StringCommon.isNullOrBlank(user.getName())) this.setName(user.getName());
        if (!StringCommon.isNullOrBlank(user.getAddress())) this.setAddress(user.getAddress());
        if (!StringCommon.isNullOrBlank(user.getPhone())) this.setPhone(user.getPhone());
        if (!StringCommon.isNullOrBlank(user.getEmail())) this.setEmail(user.getEmail());
        if (user.getType() != null) this.setType(user.getType());
        if (user.getStatus() != null) this.setStatus(user.getStatus());
        return this;
    }
}

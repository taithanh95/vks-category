package com.bitsco.vks.category.entities;


import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = Constant.TABLE.PROVINCE)
public class Province extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SEQUENCE.SQ_PROVINCE)
    @SequenceGenerator(name = Constant.SEQUENCE.SQ_PROVINCE, sequenceName = Constant.SEQUENCE.SQ_PROVINCE, allocationSize = 1)
    private Long id;
    @Column(name = "S_CODE")
    private String code;
    @Column(name = "S_NAME")
    private String name;
    @Column(name = "S_NAME_NOSIGN")
    private String nameNoSign;
    @Transient
    private List<District> districtList;

    public void setCode(String code) {
        if (!StringCommon.isNullOrBlank(code))
            code = code.trim().toUpperCase();
        this.code = code;
    }

    public void setName(String name) {
        if (!StringCommon.isNullOrBlank(name)) {
            this.name = name.trim();
            this.nameNoSign = StringCommon.removeSpecialCharAndToNoSign(name).toUpperCase();
        }
    }

    public Province coppyFrom(Province province) {
        if (!StringCommon.isNullOrBlank(province.getCode())) this.setCode(province.getCode());
        if (!StringCommon.isNullOrBlank(province.getName())) this.setName(province.getName());
        if (province.getStatus() != null) this.setStatus(province.getStatus());
        return this;
    }
}

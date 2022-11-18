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
@Table(name = Constant.TABLE.DISTRICT)
public class District extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SEQUENCE.SQ_DISTRICT)
    @SequenceGenerator(name = Constant.SEQUENCE.SQ_DISTRICT, sequenceName = Constant.SEQUENCE.SQ_DISTRICT, allocationSize = 1)
    private Long id;
    @Column(name = "S_CODE")
    private String code;
    @Column(name = "S_NAME")
    private String name;
    @Column(name = "S_NAME_NOSIGN")
    private String nameNoSign;
    @Column(name = "S_PROVINCE_CODE")
    private String provinceCode;

    @Transient
    private Province province;

    @Transient
    private List<Commune> communeList;

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

    public District coppyFrom(District district) {
        if (!StringCommon.isNullOrBlank(district.getCode())) this.setCode(district.getCode());
        if (!StringCommon.isNullOrBlank(district.getName())) this.setName(district.getName());
        if (!StringCommon.isNullOrBlank(district.getProvinceCode())) this.setProvinceCode(district.getProvinceCode());
        if (district.getStatus() != null) this.setStatus(district.getStatus());
        return this;
    }

}

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
@Table(name = Constant.TABLE.COMMUNE)
public class Commune extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SEQUENCE.SQ_COMMUNE)
    @SequenceGenerator(name = Constant.SEQUENCE.SQ_COMMUNE, sequenceName = Constant.SEQUENCE.SQ_COMMUNE, allocationSize = 1)
    private Long id;
    @Column(name = "S_CODE")
    private String code;
    @Column(name = "S_NAME")
    private String name;
    @Column(name = "S_NAME_NOSIGN")
    private String nameNoSign;
    @Column(name = "S_PROVINCE_CODE")
    private String provinceCode;
    @Column(name = "S_DISTRICT_CODE")
    private String districtCode;
    @Column(name = "S_LEVEL")
    private String level;

    @Transient
    private Province province;
    @Transient
    private District district;
    @Transient
    private List<Village> villageList;

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

    public Commune coppyFrom(Commune commune) {
        if (!StringCommon.isNullOrBlank(commune.getCode())) this.setCode(commune.getCode());
        if (!StringCommon.isNullOrBlank(commune.getName())) this.setName(commune.getName());
        if (!StringCommon.isNullOrBlank(commune.getDistrictCode())) this.setDistrictCode(commune.getDistrictCode());
        if (!StringCommon.isNullOrBlank(commune.getLevel())) this.setLevel(commune.getLevel());
        if (commune.getStatus() != null) this.setStatus(commune.getStatus());
        return this;
    }
}

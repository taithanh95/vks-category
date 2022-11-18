package com.bitsco.vks.category.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = Constant.TABLE.BANK)
public class Bank extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SEQUENCE.SQ_BANK)
    @SequenceGenerator(name = Constant.SEQUENCE.SQ_BANK, sequenceName = Constant.SEQUENCE.SQ_BANK, allocationSize = 1)
    private Long id;

    @Column(name = "S_CODE")
    private String code;

    @Column(name = "S_NAME")
    private String name;

    @Column(name = "S_NAME_EN")
    private String nameEn;

    @Column(name = "S_SWIFT_CODE")
    private String swiftCode;

    @Column(name = "S_ALIAS")
    private String alias;

    public Bank coppyFrom(Bank bank) {
        if (!StringCommon.isNullOrBlank(bank.getCode())) this.setCode(bank.getCode());
        if (!StringCommon.isNullOrBlank(bank.getName())) this.setName(bank.getName());
        if (!StringCommon.isNullOrBlank(bank.getNameEn())) this.setNameEn(bank.getNameEn());
        if (!StringCommon.isNullOrBlank(bank.getSwiftCode())) this.setSwiftCode(bank.getSwiftCode());
        if (!StringCommon.isNullOrBlank(bank.getAlias())) this.setAlias(bank.getAlias());
        if (bank.getStatus() != null) this.setStatus(bank.getStatus());
        return this;
    }
}

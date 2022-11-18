package com.bitsco.vks.category.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 28/12/2021
 * Time: 10:58 AM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = Constant.TABLE.REPORT)
public class Report extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SEQUENCE.SQ_REPORT)
    @SequenceGenerator(name = Constant.SEQUENCE.SQ_REPORT, sequenceName = Constant.SEQUENCE.SQ_REPORT, allocationSize = 1)
    private Long id;

    @Column(name = "S_REPORT_CODE")
    private String reportCode;

    @Column(name = "S_CODE")
    private String code;

    @Column(name = "S_NAME")
    private String name;

    @Column(name = "S_PARENT")
    private String parent;

    @Column(name = "N_LEVEL")
    private Integer level;

    public Report coppyFrom(Report report) {
        if (!StringCommon.isNullOrBlank(report.getCode())) this.setCode(report.getCode());
        if (!StringCommon.isNullOrBlank(report.getName())) this.setName(report.getName());
        if (report.getLevel() != null) this.setLevel(report.getLevel());
        if (report.getStatus() != null) this.setStatus(report.getStatus());
        return this;
    }
}


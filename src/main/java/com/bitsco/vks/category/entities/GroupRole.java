package com.bitsco.vks.category.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: truongnq
 * @date: 26-Dec-18 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = Constant.TABLE.GROUP_ROLE)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupRole extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SEQUENCE.SQ_GROUP_ROLE)
    @SequenceGenerator(name = Constant.SEQUENCE.SQ_GROUP_ROLE, sequenceName = Constant.SEQUENCE.SQ_GROUP_ROLE, allocationSize = 1)
    private Long id;
    @Column(name = "s_code")
    private String code;
    @Column(name = "s_name")
    private String name;
    @Column(name = "s_url")
    private String url;
    @Column(name = "s_icon")
    private String icon;
    @Column(name = "n_type")
    private Integer type;
    @Column(name = "n_priority")
    private Integer priority;

    public GroupRole coppyFrom(GroupRole groupRole) {
        if (!StringCommon.isNullOrBlank(groupRole.getName())) {
            this.setName(groupRole.getName());
            this.setCode(
                    this.getUrl().trim().substring(1, this.getUrl().length()).toUpperCase().replaceAll("/", "_") + "_" +
                            StringCommon.toNoSign(groupRole.getName().trim()).toUpperCase().replaceAll(" ", "_")
            );
        }
        if (!StringCommon.isNullOrBlank(groupRole.getUrl())) {
            this.setUrl(groupRole.getUrl());
            this.setCode(
                    groupRole.getUrl().trim().substring(1, groupRole.getUrl().length()).toUpperCase().replaceAll("/", "_") + "_" +
                            StringCommon.toNoSign(this.getName().trim()).toUpperCase().replaceAll(" ", "_")
            );
        }
        if (!StringCommon.isNullOrBlank(groupRole.getIcon())) this.setIcon(groupRole.getIcon());
        if (groupRole.getType() != null) this.setType(groupRole.getType());
        if (groupRole.getStatus() != null) this.setStatus(groupRole.getStatus());
        if (groupRole.getPriority() != null) this.setPriority(groupRole.getPriority());
        return this;
    }

    public GroupRole(Long id, String name, String url, Integer status) {
        this.setId(id);
        this.name = name;
        this.url = url;
        this.setStatus(status);
    }

    public GroupRole(String url, Integer status) {
        this.url = url;
        this.setStatus(status);
    }

    public void setName(String name) {
        if (!StringCommon.isNullOrBlank(name))
            this.name = name.trim();
    }

    public void setUrl(String url) {
        if (!StringCommon.isNullOrBlank(url))
            this.url = url.toLowerCase().trim();
    }
}

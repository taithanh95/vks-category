package com.bitsco.vks.category.service;

import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.entities.Report;
import com.bitsco.vks.category.repository.ReportRepository;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 28/12/2021
 * Time: 11:33 AM
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    CacheService cacheService;

    private Report save(Report report) throws Exception {
        if (report.getId() != null) report.setUpdatedBy(cacheService.getUsernameFromHeader());
        else report.setCreatedBy(cacheService.getUsernameFromHeader());
        return reportRepository.save(report);
    }

    @Override
    public Report createOrUpdate(Report report) throws Exception {
        if (report.getId() == null) {
            report.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            return save(report);
        }
        Report old = reportRepository.findById(report.getId()).orElse(null);
        return save(old.coppyFrom(report));
    }

    @Override
    public Report delete(long id) throws Exception {
        ValidateCommon.validateNullObject(id, "id");
        Report old = reportRepository.findById(id).orElse(null);
        if (old == null) {
            throw new CommonException(Response.DATA_NOT_FOUND);
        } else old.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        return reportRepository.save(old);
    }

    @Override
    public Page<Report> getList(Report report, Pageable pageable) throws Exception {
        return reportRepository.getList(
                StringCommon.isNullOrBlank(report.getReportCode()) ? null : report.getReportCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(report.getName()) ? null : ("%" + report.getName().trim().toUpperCase() + "%"),
                report.getLevel() == null ? null : report.getLevel(),
                report.getStatus(), pageable
        );
    }
}

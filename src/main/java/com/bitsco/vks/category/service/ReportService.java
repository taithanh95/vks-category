package com.bitsco.vks.category.service;

import com.bitsco.vks.category.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 28/12/2021
 * Time: 11:32 AM
 */
public interface ReportService {
    Report createOrUpdate(Report report) throws Exception;

    Report delete(long id) throws Exception;

    Page<Report> getList(Report report, Pageable pageable) throws Exception;
}

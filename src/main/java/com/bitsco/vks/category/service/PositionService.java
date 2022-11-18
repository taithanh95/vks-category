package com.bitsco.vks.category.service;

import com.bitsco.vks.category.entities.GroupRole;
import com.bitsco.vks.category.entities.Position;
import com.bitsco.vks.category.entities.PositionGroupRole;

import java.util.List;

public interface PositionService {
    Position createPosition(Position position) throws Exception;

    Position updatePosition(Position position) throws Exception;

    List<Position> findBySupplierIdAndUserType(Position position) throws Exception;

    List<Position> getListPosition(Position position) throws Exception;

    List<Long> getListGroupRoleId(Position position) throws Exception;

    PositionGroupRole createPositionGroupRole(PositionGroupRole positionGroupRole) throws Exception;

    PositionGroupRole updatePositionGroupRole(PositionGroupRole positionGroupRole) throws Exception;

    PositionGroupRole updateStatusPositionGroupRole(PositionGroupRole positionGroupRole) throws Exception;

    List<PositionGroupRole> getListPositionGroupRole(PositionGroupRole positionGroupRole) throws Exception;

    List<GroupRole> getListGroupRoleByPosition(PositionGroupRole positionGroupRole) throws Exception;
}

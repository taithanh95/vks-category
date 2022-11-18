package com.bitsco.vks.category.service;

import com.bitsco.vks.category.entities.Commune;
import com.bitsco.vks.category.entities.District;
import com.bitsco.vks.category.entities.Province;
import com.bitsco.vks.category.entities.Village;
import com.bitsco.vks.category.request.AreaRequest;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;

import java.util.List;

public interface AreaService {
    Province createOrUpdateProvince(Province province) throws Exception;

    District createOrUpdateDistrict(District district) throws Exception;

    Commune createOrUpdateCommune(Commune commune) throws Exception;

    Village createOrUpdateVillage(Village village) throws Exception;

    List<District> findDistrictByProvinceCode(District district) throws Exception;

    List<Commune> findCommuneByDistrictCode(Commune commune) throws Exception;

    List<Village> findVillageByCommuneCode(Village village) throws Exception;

    List<Province> getListProvince(Province province) throws Exception;

    PageResponse getPageDistrict(PageRequest pageRequest) throws Exception;

    PageResponse getPageCommune(PageRequest pageRequest) throws Exception;

    PageResponse getPageVillage(PageRequest pageRequest) throws Exception;

    Response importArea(AreaRequest areaRequest) throws Exception;

    Village updateVillageStatus(long id, int status) throws Exception;

    Commune updateCommuneStatus(long id, int status) throws Exception;

    District updateDistrictStatus(long id, int status) throws Exception;

    Province updateProvinceStatus(long id, int status) throws Exception;

    Province findProvinceByCode(String provinceCode) throws Exception;

    District findDistrictByCode(String districtCode) throws Exception;

    Commune findCommuneByCode(String communeCode) throws Exception;
}

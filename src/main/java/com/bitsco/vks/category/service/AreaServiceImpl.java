package com.bitsco.vks.category.service;

import com.bitsco.vks.category.repository.DistrictRepository;
import com.bitsco.vks.category.repository.ProvinceRepository;
import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.entities.Commune;
import com.bitsco.vks.category.entities.District;
import com.bitsco.vks.category.entities.Province;
import com.bitsco.vks.category.entities.Village;
import com.bitsco.vks.category.repository.CommuneRepository;
import com.bitsco.vks.category.repository.VillageRepository;
import com.bitsco.vks.category.request.AreaRequest;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.constant.MessageContent;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.MessageCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    CommuneRepository communeRepository;
    @Autowired
    VillageRepository villageRepository;
    @Autowired
    CacheService cacheService;

    private Province saveProvince(Province province) throws Exception {
        if (province.getId() != null) province.setUpdatedBy(cacheService.getUsernameFromHeader());
        else province.setCreatedBy(cacheService.getUsernameFromHeader());
        province = provinceRepository.save(province);
        cacheService.addProvince2RedisCache(province);
        return province;
    }

    private District saveDistrict(District district) throws Exception {
        if (district.getId() != null) district.setUpdatedBy(cacheService.getUsernameFromHeader());
        else district.setCreatedBy(cacheService.getUsernameFromHeader());
        district = districtRepository.save(district);
        cacheService.addDistrict2RedisCache(district);
        return district;
    }

    private Commune saveCommune(Commune commune) throws Exception {
        if (commune.getId() != null) commune.setUpdatedBy(cacheService.getUsernameFromHeader());
        else commune.setCreatedBy(cacheService.getUsernameFromHeader());
        commune = communeRepository.save(commune);
        cacheService.addCommune2RedisCache(commune);
        return commune;
    }

    private Village saveVillage(Village village) throws Exception {
        if (village.getId() != null) village.setUpdatedBy(cacheService.getUsernameFromHeader());
        else village.setCreatedBy(cacheService.getUsernameFromHeader());
        return villageRepository.save(village);
    }

    @Override
    public Province createOrUpdateProvince(Province province) throws Exception {
        ValidateCommon.validateNullObject(province.getCode(), "code");
        Province provinceOld = findProvinceByCode(province.getCode());
        if (provinceOld == null) {
            ValidateCommon.validateNullObject(province.getName(), "name");
            province.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            return saveProvince(province);
        } else return saveProvince(provinceOld.coppyFrom(province));
    }

    @Override
    public District createOrUpdateDistrict(District district) throws Exception {
        ValidateCommon.validateNullObject(district.getCode(), "code");
        District districtOld = findDistrictByCode(district.getCode());
        if (districtOld == null) {
            ValidateCommon.validateNullObject(district.getName(), "name");
            district.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            return saveDistrict(district);
        } else return saveDistrict(districtOld.coppyFrom(district));
    }

    @Override
    public Commune createOrUpdateCommune(Commune commune) throws Exception {
        ValidateCommon.validateNullObject(commune.getCode(), "code");
        Commune communeOld = findCommuneByCode(commune.getCode());
        if (communeOld == null) {
            ValidateCommon.validateNullObject(commune.getName(), "name");
            commune.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            return saveCommune(commune);
        } else return saveCommune(communeOld.coppyFrom(commune));
    }

    @Override
    public Village createOrUpdateVillage(Village village) throws Exception {
        ValidateCommon.validateNullObject(village.getCode(), "code");
        Village villageOld = villageRepository.findFirstByCode(village.getCode());
        if (villageOld == null) {
            ValidateCommon.validateNullObject(village.getName(), "name");
            village.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            return saveVillage(village);
        } else return saveVillage(villageOld.coppyFrom(village));
    }

    @Override
    public List<District> findDistrictByProvinceCode(District district) throws Exception {
        ValidateCommon.validateNullObject(district.getProvinceCode(), "provinceCode");
        return districtRepository.findByProvinceCodeOrderByCode(district.getProvinceCode());
    }

    @Override
    public List<Commune> findCommuneByDistrictCode(Commune commune) throws Exception {
        ValidateCommon.validateNullObject(commune.getDistrictCode(), "districtCode");
        return communeRepository.findByDistrictCodeAndStatusOrderByName(commune.getDistrictCode(), Constant.STATUS_OBJECT.ACTIVE);
    }

    @Override
    public List<Village> findVillageByCommuneCode(Village village) throws Exception {
        ValidateCommon.validateNullObject(village.getCommuneCode(), "communeCode");
        return villageRepository.findByCommuneCodeAndStatusOrderByName(village.getCommuneCode(), Constant.STATUS_OBJECT.ACTIVE);
    }

    @Override
    public List<Province> getListProvince(Province province) throws Exception {
        return provinceRepository.getList(
                StringCommon.isNullOrBlank(province.getCode()) ? null : province.getCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(province.getName()) ? null : ("%" + province.getNameNoSign() + "%"),
                province.getStatus()
        );
    }

    @Override
    public PageResponse getPageDistrict(PageRequest pageRequest) throws Exception {
        District district = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), District.class);
        List<District> list = districtRepository.getPage(
                StringCommon.isNullOrBlank(district.getCode()) ? null : district.getCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(district.getName()) ? null : ("%" + district.getNameNoSign() + "%"),
                district.getStatus(),
                StringCommon.isNullOrBlank(district.getProvinceCode()) ? null : district.getProvinceCode().trim().toUpperCase()
        );
        PageResponse pageResponse = PageCommon.toPageResponse(list, pageRequest.getPageNumber(), pageRequest.getPageSize());
        if (!CollectionUtils.isEmpty(pageResponse.getData()))
            pageResponse.getData().stream().forEach(x -> {
                District item = (District) x;
                item.setProvince(findProvinceByCode(item.getProvinceCode()));
            });
        return pageResponse;
    }

    @Override
    public PageResponse getPageCommune(PageRequest pageRequest) throws Exception {
        Commune commune = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), Commune.class);
        List<Commune> list = communeRepository.getPage(
                StringCommon.isNullOrBlank(commune.getCode()) ? null : commune.getCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(commune.getName()) ? null : ("%" + commune.getNameNoSign() + "%"),
                commune.getStatus(),
                StringCommon.isNullOrBlank(commune.getProvinceCode()) ? null : commune.getProvinceCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(commune.getDistrictCode()) ? null : commune.getDistrictCode().trim().toUpperCase()
        );
        PageResponse pageResponse = PageCommon.toPageResponse(list, pageRequest.getPageNumber(), pageRequest.getPageSize());
        if (!CollectionUtils.isEmpty(pageResponse.getData()))
            pageResponse.getData().stream().forEach(x -> {
                Commune item = (Commune) x;
                item.setDistrict(findDistrictByCode(item.getDistrictCode()));
                if (item.getDistrict() != null && !StringCommon.isNullOrBlank(item.getDistrict().getProvinceCode()))
                    item.setProvince(findProvinceByCode(item.getDistrict().getProvinceCode()));
            });
        return pageResponse;
    }

    @Override
    public PageResponse getPageVillage(PageRequest pageRequest) throws Exception {
        Village village = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), Village.class);
        List<Village> list = villageRepository.getPage(
                StringCommon.isNullOrBlank(village.getCode()) ? null : village.getCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(village.getName()) ? null : ("%" + village.getNameNoSign() + "%"),
                village.getStatus(),
                StringCommon.isNullOrBlank(village.getCommuneCode()) ? null : village.getCommuneCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(village.getDistrictCode()) ? null : village.getDistrictCode().trim().toUpperCase(),
                StringCommon.isNullOrBlank(village.getProvinceCode()) ? null : village.getProvinceCode().trim().toUpperCase()
        );
        PageResponse pageResponse = PageCommon.toPageResponse(list, pageRequest.getPageNumber(), pageRequest.getPageSize());
        if (!CollectionUtils.isEmpty(pageResponse.getData()))
            pageResponse.getData().stream().forEach(x -> {
                Village item = (Village) x;
                if (!StringCommon.isNullOrBlank(item.getCommuneCode()))
                    item.setCommune(findCommuneByCode(item.getCommuneCode()));
                if (!StringCommon.isNullOrBlank(item.getDistrictCode()))
                    item.setDistrict(findDistrictByCode(item.getDistrictCode()));
                if (!StringCommon.isNullOrBlank(item.getProvinceCode()))
                    item.setProvince(findProvinceByCode(item.getProvinceCode()));
            });
        return pageResponse;
    }

    @Override
    public Response importArea(AreaRequest areaRequest) throws Exception {
        createOrUpdateProvince(areaRequest.getProvince());
        createOrUpdateDistrict(areaRequest.getDistrict());
        createOrUpdateCommune(areaRequest.getCommune());
        return Response.SUCCESS;
    }

    @Override
    public Village updateVillageStatus(long id, int status) throws Exception {
        Village village = villageRepository.findById(id).orElse(null);
        if (village == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, MessageCommon.getMessage(MessageContent.OBJECT_NOT_FOUND_BY_FIELD_VALUE,
                    new String[]{"Village", "id", id + ""}));
        village.setStatus(status);
        village.setUpdatedBy(cacheService.getUsernameFromHeader());
        return villageRepository.save(village);
    }

    @Override
    public Commune updateCommuneStatus(long id, int status) throws Exception {
        Commune commune = communeRepository.findById(id).orElse(null);
        if (commune == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, MessageCommon.getMessage(MessageContent.OBJECT_NOT_FOUND_BY_FIELD_VALUE,
                    new String[]{"Commune", "id", id + ""}));
        if (commune.getStatus() == 0)
            commune.setStatus(1);
        else
            commune.setStatus(0);
        commune.setUpdatedBy(cacheService.getUsernameFromHeader());
        return communeRepository.save(commune);
    }

    @Override
    public District updateDistrictStatus(long id, int status) throws Exception {
        District district = districtRepository.findById(id).orElse(null);
        if (district == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, MessageCommon.getMessage(MessageContent.OBJECT_NOT_FOUND_BY_FIELD_VALUE,
                    new String[]{"District", "id", id + ""}));
        if (district.getStatus() == 0)
            district.setStatus(1);
        else
            district.setStatus(0);
        district.setUpdatedBy(cacheService.getUsernameFromHeader());
        return districtRepository.save(district);
    }

    @Override
    public Province updateProvinceStatus(long id, int status) throws Exception {
        Province district = provinceRepository.findById(id).orElse(null);
        if (district == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, MessageCommon.getMessage(MessageContent.OBJECT_NOT_FOUND_BY_FIELD_VALUE,
                    new String[]{"Province", "id", id + ""}));
        if (district.getStatus() == 0)
            district.setStatus(1);
        else
            district.setStatus(0);
        district.setUpdatedBy(cacheService.getUsernameFromHeader());
        return provinceRepository.save(district);
    }

    @Override
    public Province findProvinceByCode(String provinceCode) {
        Province province = cacheService.getProvinceFromCache(provinceCode);
        if (province == null)
            return provinceRepository.findFirstByCode(provinceCode);
        return province;
    }

    @Override
    public District findDistrictByCode(String districtCode) {
        District district = cacheService.getDistrictFromCache(districtCode);
        if (district == null)
            return districtRepository.findFirstByCode(districtCode);
        return district;
    }

    @Override
    public Commune findCommuneByCode(String communeCode) {
        Commune commune = cacheService.getCommuneFromCache(communeCode);
        if (commune == null)
            return communeRepository.findFirstByCode(communeCode);
        return commune;
    }
}

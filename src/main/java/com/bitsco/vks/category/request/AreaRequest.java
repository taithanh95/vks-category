package com.bitsco.vks.category.request;

import com.bitsco.vks.category.entities.District;
import com.bitsco.vks.category.entities.Province;
import com.bitsco.vks.category.entities.Commune;
import com.bitsco.vks.common.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaRequest {
    private String provinceCode;
    private String provinceName;
    private String districtCode;
    private String districtName;
    private String communeCode;
    private String communeName;

    public Province getProvince() {
        Province province = new Province();
        province.setCode(this.provinceCode);
        province.setName(this.provinceName);
        province.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        return province;
    }

    public District getDistrict() {
        District district = new District();
        district.setCode(this.districtCode);
        district.setName(this.districtName);
        district.setProvinceCode(this.provinceCode);
        district.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        return district;
    }

    public Commune getCommune() {
        Commune commune = new Commune();
        commune.setCode(this.communeCode);
        commune.setName(this.communeName);
        commune.setProvinceCode(this.provinceCode);
        commune.setDistrictCode(this.districtCode);
        commune.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        return commune;
    }
}

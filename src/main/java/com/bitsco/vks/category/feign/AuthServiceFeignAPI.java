package com.bitsco.vks.category.feign;

import com.bitsco.vks.category.entities.GroupRole;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.response.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(Constant.FEIGN_CLIENT.SSO)
public interface AuthServiceFeignAPI {
    @GetMapping(value = "ping")
    String ping();

    @PostMapping("/role/getListGroupRole/")
    ResponseBody getListGroupRole(@RequestBody GroupRole groupRole);
}

package com.bitsco.vks.category.api;

import com.bitsco.vks.category.entities.District;
import com.bitsco.vks.category.service.RedisService;
import com.bitsco.vks.category.cache.CacheService;
import com.bitsco.vks.category.entities.Commune;
import com.bitsco.vks.category.entities.Province;
import com.bitsco.vks.category.entities.User;
import com.bitsco.vks.category.request.LoadCacheRequest;
import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "cache")
public class CacheController {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @Autowired
    RedisService redisService;

    @Autowired
    CacheService cacheService;

    @Operation(description = "Thực hiện đẩy dữ liệu lên cache")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/pushRedisCache/")
    public ResponseEntity<?> pushRedisCache(@RequestBody LoadCacheRequest loadCacheRequest) throws Exception {
        try {
            boolean rs = false;
            if (loadCacheRequest.isParam())
                rs = redisService.pushAllParamToRedisCache();
            if (loadCacheRequest.isUser())
                rs = redisService.pushAllUserToRedisCache();
            if (loadCacheRequest.isArea())
                rs = redisService.pushAllAreaToRedisCache();
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, rs), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /cache/pushRedisCache/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Xóa dữ liệu trên cache")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/deleteRedisCache/")
    public ResponseEntity<?> deleteRedisCache(@RequestBody LoadCacheRequest loadCacheRequest) throws Exception {
        try {
            boolean rs = false;
            if (loadCacheRequest.isUser())
                rs = redisService.deleteAllUserFromRedisCache();
            if (loadCacheRequest.isArea())
                rs = redisService.deleteAllAreaFromRedisCache();
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, rs), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /cache/deleteRedisCache/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn thông tin user trên redis cache")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getUserFromRedisCache/")
    public ResponseEntity<?> getUserFromRedisCache(@RequestBody User user) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, cacheService.getUserFromCache(user.getUsername())), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /cache/getUserFromRedisCache/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn thông tin tỉnh/thành phố trên redis cache")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getProvinceFromRedisCache/")
    public ResponseEntity<?> getProvinceFromRedisCache(@RequestBody Province province) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, cacheService.getProvinceFromCache(province.getCode())), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /cache/getProvinceFromRedisCache/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn thông tin quận huyện trên redis cache")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getDistrictFromRedisCache/")
    public ResponseEntity<?> getDistrictFromRedisCache(@RequestBody District district) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, cacheService.getDistrictFromCache(district.getCode())), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /cache/getDistrictFromRedisCache/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn thông tin phường xã trên redis cache")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getCommuneFromRedisCache/")
    public ResponseEntity<?> getCommuneFromRedisCache(@RequestBody Commune commune) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, cacheService.getCommuneFromCache(commune.getCode())), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /cache/getCommuneFromRedisCache/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

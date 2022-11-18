package com.bitsco.vks.category.api;

import com.bitsco.vks.category.entities.Position;
import com.bitsco.vks.category.entities.PositionGroupRole;
import com.bitsco.vks.category.service.PositionService;
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
@RequestMapping(value = "position")
public class PositionController {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @Autowired
    PositionService positionService;

    @Operation(description = "Thêm mới chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/createPosition/")
    public ResponseEntity<?> createPosition(@RequestBody Position position) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.createPosition(position)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/createPosition/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Cập nhật chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/updatePosition/")
    public ResponseEntity<?> updatePosition(@RequestBody Position position) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.updatePosition(position)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/updatePosition/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Tra cứu chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getListPosition/")
    public ResponseEntity<?> getListPosition(@RequestBody Position position) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.getListPosition(position)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/getListPosition/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Thêm mới phân quyền chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/createPositionGroupRole/")
    public ResponseEntity<?> createPositionGroupRole(@RequestBody PositionGroupRole positionGroupRole) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.createPositionGroupRole(positionGroupRole)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/createPositionGroupRole/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Cập nhật phân quyền chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/updatePositionGroupRole/")
    public ResponseEntity<?> updatePositionGroupRole(@RequestBody PositionGroupRole positionGroupRole) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.updatePositionGroupRole(positionGroupRole)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/updatePositionGroupRole/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Cập nhật trạng thái phân quyền chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/updateStatusPositionGroupRole/")
    public ResponseEntity<?> updateStatusPositionGroupRole(@RequestBody PositionGroupRole positionGroupRole) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.updateStatusPositionGroupRole(positionGroupRole)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/updateStatusPositionGroupRole/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Tra cứu phân quyền chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getListPositionGroupRole/")
    public ResponseEntity<?> getListPositionGroupRole(@RequestBody PositionGroupRole positionGroupRole) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.getListPositionGroupRole(positionGroupRole)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/getListPositionGroupRole/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Tra cứu nhóm quyền theo chức vụ của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getListGroupRoleByPosition/")
    public ResponseEntity<?> getListGroupRoleByPosition(@RequestBody PositionGroupRole positionGroupRole) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.getListGroupRoleByPosition(positionGroupRole)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/getListGroupRoleByPosition/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn danh sách nhóm quyền theo loại tài khoản của nhà cung cấp")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getListGroupRoleId/")
    public ResponseEntity<?> getListGroupRoleId(@RequestBody Position position) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, positionService.getListGroupRoleId(position)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /position/getListGroupRoleId/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

}

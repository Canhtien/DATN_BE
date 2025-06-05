package com.alibou.security.api.guest;

import com.alibou.security.service.AreaService;
//import com.viettel.etc.xlibrary.core.constants.FunctionCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/guest/areas")
public class AreaAPI {
    @Autowired
    private AreaService areaService;

    /**
     * Lay danh tinh, thanh pho
     *
     * @param authentication: thong tin nguoi dung
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getArea(@AuthenticationPrincipal Authentication authentication) {

        Object resultObj = areaService.getArea(null, null);
//        return new ResponseEntity<>(FunctionCommon.responseToClient(authentication, resultObj), HttpStatus.OK);
        return ResponseEntity.ok(resultObj);
    }

    /**
     * Lay danh sach quan/ huyen, phuong/ xa
     *
     * @param authentication: thong tin nguoi dung
     * @return
     */
    @RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getArea(@AuthenticationPrincipal Authentication authentication,
                                          @PathVariable("code") String parentCode) {

        Object resultObj = areaService.getArea(parentCode, null);
//        return new ResponseEntity<>(FunctionCommon.responseToClient(authentication, resultObj), HttpStatus.OK);
        return ResponseEntity.ok(resultObj);
    }


    /**
     * Lay chi tiet dia chi theo area code
     *
     * @param authentication: thong tin nguoi dung
     * @return
     */
    @RequestMapping(value = "/{code}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAreaByAreaCode(@AuthenticationPrincipal Authentication authentication,
                                                    @PathVariable("code") String areaCode) {

        Object resultObj = areaService.getArea(null, areaCode);
//        return new ResponseEntity<>(FunctionCommon.responseToClient(authentication, resultObj), HttpStatus.OK);
        return ResponseEntity.ok(resultObj);
    }
}
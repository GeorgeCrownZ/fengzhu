package com.soft.web.controller;

import com.soft.web.common.AjaxResult;
import com.soft.web.service.AboutMeService;
import com.soft.web.vo.AboutMeVO;
import com.soft.web.vo.EditAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/self")
@CrossOrigin
public class AboutMeController {

    @Autowired
    AboutMeService aboutMeService;
    @Autowired

    @PostMapping("/adpic")
    public Object adpic() {
        return aboutMeService.adpic();
    }

    @PostMapping("/editinfo")
    public AboutMeVO editinfo(Long uid) {
        return aboutMeService.editinfo(uid);
    }

    @PostMapping("/saveinfo")
    public AjaxResult saveinfo(AboutMeVO aboutMeVO) throws IOException {
        return aboutMeService.saveinfo(aboutMeVO);
    }

    @PostMapping("/order")
    public Object order(Long uid, String type) {
        return aboutMeService.order(uid, type);
    }

    @PostMapping("/received")
    public AjaxResult received(Long uid, Long id) {
        return aboutMeService.received(uid, id);
    }

    @PostMapping("/editaddress")
    public AjaxResult editaddress(EditAddressVO editAddrVO) {
        return aboutMeService.editaddress(editAddrVO);
    }

    @PostMapping("deleteaddress")
    public AjaxResult deleteaddress(Long aid) {
        return aboutMeService.deleteaddress(aid);
    }
}

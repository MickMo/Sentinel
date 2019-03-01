package com.alibaba.csp.sentinel.dashboard.controller;

import com.alibaba.csp.sentinel.dashboard.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@SuppressWarnings("CheckStyle")
@Controller
public class SentinelTestController {

    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SentinelTestController.class);

    @RequestMapping(value = "/cmpp-api/external/sentinelTest")
    @ResponseBody
    public String acceptOrderV3() throws
            InterruptedException {
        //0.0-1.0
        int v = (int) (150 + (Math.random()) * (300 - 150));
        Thread.sleep(v);
        return "finished";
    }

    @RequestMapping(value = "/Test2")
    @ResponseBody
    public Map acceptOrderV32() throws
            InterruptedException {
        Map<String, String> allAuth = userService.findAllAuth();
        return allAuth;
    }
}




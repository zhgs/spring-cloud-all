package com.zhgs.eurekaprovider.controller;

import com.zhgs.eurekaprovider.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：
 *
 * @Auther: zhouguangsheng
 * @Date: 2020/4/14 13:53
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthStatusService healthStatusService;

    @GetMapping("/getUp")
    public Health getUpHealth(){
        HealthIndicator healthIndicator = () -> {
            return new Health.Builder().up().build();
        };

        return healthIndicator.getHealth(true);
    }

    @GetMapping("/getDown")
    public Health getDownHealth(){
        HealthIndicator healthIndicator = () -> {
            return new Health.Builder().down().build();
        };

        return healthIndicator.getHealth(true);
    }

    @GetMapping("/change")
    public Health health(@RequestParam("status") Boolean status) {
        healthStatusService.changeStatus(status);

        return healthStatusService.getHealth(true);
    }

}

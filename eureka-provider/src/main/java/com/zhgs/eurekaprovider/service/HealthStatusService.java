package com.zhgs.eurekaprovider.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

/**
 * 功能描述：
 *
 * @Auther: zhouguangsheng
 * @Date: 2020/4/14 15:04
 */
@Service
public class HealthStatusService implements HealthIndicator {

    private boolean status = true;

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public Health health() {
        if(status){
            return new Health.Builder().up().build();
        }
        return new Health.Builder().down().build();
    }

    public void changeStatus(Boolean status){
        this.status = status;
    }
}

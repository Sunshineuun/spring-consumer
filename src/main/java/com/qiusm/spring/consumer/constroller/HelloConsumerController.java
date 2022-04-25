package com.qiusm.spring.consumer.constroller;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qiushengming
 */
@Slf4j
@RequestMapping("hello")
@RestController
public class HelloConsumerController {
    @Resource
    private NacosServiceManager nacosServiceManager;
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @GetMapping("getNamingService")
    public void getNamingService() {
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        log.info("{}", namingService.getServerStatus());
        try {
            List<Instance> instances = namingService.getAllInstances("spring");
            log.info("{}", instances);
        } catch (NacosException e) {
            log.info("{}", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}

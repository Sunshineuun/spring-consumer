package com.qiusm.spring.consumer;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.qiusm.parent.base.model.BaseDTO;
import com.qiusm.spring.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qiushengming
 */
@Slf4j
public class HelloConsumerAppTest extends SpringConsumerApplicationTests {

    @Value("${spring.application.name}")
    private String appName;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private NacosServiceManager nacosServiceManager;
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    // @DubboReference
    private HelloService helloService;

    @Test
    void echoAppName() {
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        log.info("{}", namingService.getServerStatus());
        try {
            List<Instance> instances = namingService.getAllInstances("spring");
            log.info("{}", instances);
            Instance instance = instances.get(0);
            String path = String.format("http://%s:%s/hello/%s",
                    instance.getIp(), instance.getPort(), appName);
            ResponseEntity<BaseDTO> response = restTemplate.getForEntity(path, BaseDTO.class);
            log.info("response {}", response);
            log.info("response body {}", response.getBody());
        } catch (NacosException e) {
            log.info("{}", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Test
    void getAllInstances() {
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

    /**
     * 基于dubbo的远程服务调用
     */
    @Test
    void dubboTest() {
        String r = helloService.test("qiushengming");
        log.info("{}", r);
    }
}

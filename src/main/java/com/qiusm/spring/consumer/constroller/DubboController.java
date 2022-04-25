package com.qiusm.spring.consumer.constroller;

import com.qiusm.spring.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dubbo 测试
 *
 * @author qiushengming
 */
@Slf4j
@RestController
@RequestMapping("dubbo")
public class DubboController {
    @DubboReference(version = "1.0")
    private HelloService helloService;

    @GetMapping("hello/{name}")
    public void hello(@PathVariable String name) {
        String s = helloService.test(name);
        log.info("dubbo cell : {}", s);
    }
}

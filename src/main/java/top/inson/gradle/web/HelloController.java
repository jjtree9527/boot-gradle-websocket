package top.inson.gradle.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jingjitree
 * @description 测试代码类
 * @date 2023/4/19 15:39
 */
@Slf4j
@RestController
@RequestMapping(value = "/hello")
public class HelloController {


    @GetMapping("/say")
    public String say(){

        return "启动成功";
    }


}

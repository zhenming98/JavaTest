package com.java.test.mongodb;

import com.java.test.mongodb.bean.TestBean;
import com.java.test.mongodb.service.MongoDbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yzm
 * @date 2021/4/20 - 16:42
 */
@RestController
public class MongoDbLocalhost {

    @Resource
    MongoDbService mongoDbService;

    @PostMapping("mongodb/save")
    public void saveMongoDb(@RequestBody TestBean testBean) {
        mongoDbService.saveObj(testBean);
    }

    @GetMapping("mongodb/find")
    public void findMongoDb() {
        List<TestBean> testBeans = mongoDbService.findAll();
        System.out.println(testBeans.size());
        for (TestBean testBean : testBeans) {
            System.out.println(testBean);
        }
    }

    @GetMapping("mongodb/findId")
    public void findMongoDbId(String id) {
        TestBean testBean = mongoDbService.getTestBeanById(id);
        System.out.println(testBean);
    }

}

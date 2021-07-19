package com.java.test.mongodb.service;

import com.java.test.mongodb.bean.TestBean;
import lombok.extern.log4j.Log4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yzm
 * @date 2021/4/21 - 9:55
 */
@Log4j
@Service
public class MongoDbService {

    @Resource
    private MongoTemplate mongoTemplate;

    private final static String COLLECTION_NAME = "test";

    public void saveObj(TestBean testBean) {
        log.info("save--------");
        testBean.setCreateTime(new Date());
        mongoTemplate.save(testBean, COLLECTION_NAME);
    }

    public List<TestBean> findAll() {
        log.info("find---------------------");
        return mongoTemplate.findAll(TestBean.class);
    }

    public TestBean getTestBeanById(String id) {
        log.info("find-Id-------------------");
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, TestBean.class);
    }

}

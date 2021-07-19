package com.java.test.mongodb;

import com.java.test.util.time.DateUtil;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/4/16 - 16:46
 */
@RestController
public class MongoDbTest {

    @Resource
    private MongoTemplate mongoTemplate;

    @GetMapping("mongodb")
    public List<Long> getConditionUser(String startDate, String endDate, String typeCode, int massageCountTime) {
        massageCountTime = massageCountTime * 60;

        //时间型号过滤
        Date startTime = DateUtil.strToDate(startDate, "yyyy-MM");
        Date endTime = DateUtil.strToDate(endDate, "yyyy-MM");

        DBObject match = new BasicDBObject("$match", new BasicDBObject("typeCode", typeCode)
                .append("createTime", new BasicDBObject("$gte", startTime).append("$lte", endTime)));

        //按用户和月份分组统计按摩时长
        DBObject group = new BasicDBObject("$group",
                new BasicDBObject("_id", new BasicDBObject("userId", "$userInfo.userId")
                        .append("time", new BasicDBObject("$dateToString", new BasicDBObject("format", "%Y-%m").append("date", "$createTime"))))
                        .append("countTime", new BasicDBObject("$sum", "$program.duration")));


        //按用户分组统计按摩月数和最少按摩时长
        DBObject group1 = new BasicDBObject("$group", new BasicDBObject("_id", "$_id.userId")
                .append("countTime", new BasicDBObject("$min", "$countTime"))
                .append("massageCount", new BasicDBObject("$sum", 1)));


        //每个月都有按摩且时长满足要求的
        DBObject match1 = new BasicDBObject("$match", new BasicDBObject("massageCount", 2)
                .append("countTime", new BasicDBObject("$gte", massageCountTime)));

        List<DBObject> pipeline = new ArrayList<>();
        pipeline.add(match);
        pipeline.add(group);
        pipeline.add(group1);
        pipeline.add(match1);

        List<Map> resultMap = doMongo(pipeline);

        List<Long> result = new ArrayList<>();

        if (!resultMap.isEmpty()) {
            for (Map map : resultMap) {
                result.add((Long) map.get("_id"));
            }
        }
        return result;
    }

    @GetMapping("mongodb1")
    public void getMongoDb(String uuid, String type) {
        BasicDBObject dbObject = new BasicDBObject();

        BasicDBObject uuidObj = new BasicDBObject();
        uuidObj.put("$exists", "true");
        uuidObj.put("$eq", uuid);

        dbObject.put("eventValues.uuid", uuidObj);
        dbObject.put("type", type);
        dbObject.put("eventValues.status", "1");

        Query query = new BasicQuery(dbObject);

        Cursor cursor = mongoTemplate.getCollection("dc_event").find(dbObject);

        if (cursor != null && cursor.hasNext()) {
            System.out.println(cursor.next().toMap());
        }

    }

    private List<Map> doMongo(List<DBObject> pipeline) {
        Cursor timeCursor = mongoTemplate.getCollection("dc_user_program").aggregate(pipeline,
                AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
        List<Map> mapList = new ArrayList<>();
        timeCursor.forEachRemaining(c -> mapList.add(c.toMap()));
        return mapList;
    }

}

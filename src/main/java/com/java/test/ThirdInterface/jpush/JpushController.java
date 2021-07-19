package com.java.test.ThirdInterface.jpush;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2020/12/16 - 14:05
 */
@RestController
public class JpushController {

    @Resource
    JpushService jpushService;

    public void jpush() {
        String title = "推送标题";
        String content = "推送内容";
        Map<String, String> extrasMap = new HashMap<>();
        //此Map必须创建和实例化，但可以不添加内容
        extrasMap.put("额外附加传递内容", "App可以解析到，没有值仅实例化即可");

        //方式一：服务端控制推送内容方式
        jpushService.sendPush(title, content, extrasMap, "你业务中的别名1");
        //方式二：服务端控制推送并带返回值得方式
        jpushService.sendPushWithCallback(title, content, extrasMap, "你业务中的别名1");
        //方式三：服务端仅推送内容，客户端自定义显示的方式
        jpushService.sendCustomPush(title, content, extrasMap, "你业务中的别名1");
    }


}

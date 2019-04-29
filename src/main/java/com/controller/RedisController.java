package com.controller;

import com.example.demo.JsonUtil;
import com.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@RequestMapping("")
@Controller
@Slf4j
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private SetOperations<String, Object> setOperations;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Autowired
    private RedisService redisService;

    @RequestMapping("addStringType")
    @ResponseBody
    public String addStringType(String key, String value, String expireTime) {
        long expireTimeL = Long.valueOf(expireTime);
        try {
            valueOperations.set(key, value, expireTimeL, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("添加String数据类型失败！");
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping("getStringType")
    @ResponseBody
    public String getStringType(String key) {
        String jsonStr = JsonUtil.obj2String(valueOperations.get(key));
        return jsonStr;
    }

    @RequestMapping("delKey")
    @ResponseBody
    public String delKey(String key) {
        redisService.deleteKey(key);
        return "success";
    }

}

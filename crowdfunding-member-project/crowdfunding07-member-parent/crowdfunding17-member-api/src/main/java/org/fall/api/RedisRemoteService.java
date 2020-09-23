package org.fall.api;

import org.fall.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@FeignClient("crowd-redis")
public interface RedisRemoteService {

    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    );

    @RequestMapping("/set/redis/key/value/with/timeout/remote")
    ResultEntity<String> setRedisKeyValueWithTimeoutRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeUnit") TimeUnit timeUnit
            );

    @RequestMapping("/get/redis/value/by/key/remote")
    ResultEntity<String> getRedisValueByKeyRemote(
            @RequestParam("key") String key
    );

    @RequestMapping("/remove/redis/key/by/key/remote")
    ResultEntity<String> removeRedisKeyByKeyRemote(
            @RequestParam("key") String key
    );




}

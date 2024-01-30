package com.bopomofo.core.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bopomofo.core.entity.RestBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/weather")
public class QWeatherController {

    @Value("${qWeather.key}")
    private String API_KET;

    @Value("${qWeather.weatherDomain}")
    private String weatherDomain;

    @Value("${qWeather.geoDomain}")
    private String geoDomain;
    private final String LOCATION = "114.29,30.28";

    @GetMapping("/lookupCity")
    public RestBean<Object> lookupCity(String location) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", API_KET);
        map.put("location", location);
        map.put("number", 20);
        // 查询城市
        String cities = HttpUtil.get(String.format("%s/v2/city/lookup", geoDomain), map);
        JSONObject entries = JSONUtil.parseObj(cities);
        return RestBean.success(entries.get("location"));
    }

    @GetMapping("/sevenDayWeather")
    public RestBean<Object> sevenDayWeather(String location) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", API_KET);
        map.put("location", location);
        String res = HttpUtil.get(String.format("%s/v7/weather/7d", weatherDomain), map);
        return RestBean.success(JSONUtil.parseObj(res));
    }
}

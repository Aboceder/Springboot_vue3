package com.bopomofo.qWeather;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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

    /**
     * 查询城市
     *
     * @param location 城市名称
     * @return 查询结果
     */
    @GetMapping("/lookupCity")
    public Object lookupCity(String location) {
        HashMap<String, Object> map = new HashMap<>();
        synchronized (this) {

            map.put("key", API_KET);
            map.put("location", location);
            map.put("number", 20);
            // 查询城市
            String cities = HttpUtil.get(String.format("%s/v2/city/lookup", geoDomain), map);
            JSONObject entries = JSONUtil.parseObj(cities);
            return entries.get("location");
        }
    }

    /**
     * 查询未来7天天气
     *
     * @param location 城市名称
     * @return 查询结果
     */
    @GetMapping("/sevenDayWeather")
    public Object sevenDayWeather(String location) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", API_KET);
        map.put("location", location);
        String res = HttpUtil.get(String.format("%s/v7/weather/7d", weatherDomain), map);
        return JSONUtil.parseObj(res);
    }

    /**
     * 查询未来30天天气(免费api无权限请求)
     *
     * @param location 城市名称
     * @return 查询结果
     */
    @GetMapping("/thirtyDayWeather")
    public Object thirtyDayWeather(String location) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", API_KET);
        map.put("location", location);
        String res = HttpUtil.get(String.format("%s/v7/weather/30d", weatherDomain), map);
        return JSONUtil.parseObj(res);
    }
}

package com.jeasy.doc.controller;

import com.google.common.collect.Maps;
import com.jeasy.base.web.controller.ControllerSupport;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.str.StrKit;
import com.jeasy.doc.util.MonitorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Controller
@RequestMapping("/monitor")
public class MonitorController extends ControllerSupport {

    private static final String CONTROLLER = "controller";

    private static final String SERVICE = "service";

    private static final String DAO = "dao";

    @RequestMapping(value = "index")
    public String index() {
        return "monitor_index";
    }

    @RequestMapping(value = "report")
    @ResponseBody
    public void report(@RequestParam("type") String type) {
        Map<String, Object> result = Maps.newHashMap();
        List<Map<String, String>> items = new ArrayList<>();

        ConcurrentMap<String, AtomicLong> timeMap = Maps.newConcurrentMap();
        ConcurrentMap<String, AtomicLong> countMap = Maps.newConcurrentMap();

        if (StrKit.equalsIgnoreCase(CONTROLLER, type)) {
            timeMap = MonitorUtils.controllerTimeMap;
            countMap = MonitorUtils.controllerCountMap;
        } else if (StrKit.equalsIgnoreCase(SERVICE, type)) {
            timeMap = MonitorUtils.serviceTimeMap;
            countMap = MonitorUtils.serviceCountMap;
        } else if (StrKit.equalsIgnoreCase(DAO, type)) {
            timeMap = MonitorUtils.daoTimeMap;
            countMap = MonitorUtils.daoCountMap;
        }

        for (Map.Entry<String, AtomicLong> entry : timeMap.entrySet()) {
            String key = entry.getKey();
            Map<String, String> item = Maps.newHashMap();
            item.put("method", key);
            item.put("totalCount", String.valueOf(countMap.get(key).longValue()));
            item.put("totalTime", String.valueOf(timeMap.get(key).longValue()));
            item.put("avgTime", String.valueOf(timeMap.get(key).longValue() / countMap.get(key).longValue()));
            item.put("exceptionCount", String.valueOf(MonitorUtils.exceptionCountMap.get(key) == null ? 0 : MonitorUtils.exceptionCountMap.get(key).longValue()));
            items.add(item);
        }

        result.put("total", items.size());
        result.put("rows", items);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, result);
    }
}

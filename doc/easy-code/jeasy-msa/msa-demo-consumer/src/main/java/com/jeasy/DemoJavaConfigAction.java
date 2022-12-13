package com.jeasy;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jeasy.bid.*;
import com.jeasy.user.User;
import com.jeasy.user.facade.AnotherUserRestService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Component
public class DemoJavaConfigAction {

    @Reference
    private BidService bidService;

    @Reference
    private AnotherUserRestService anotherUserRestService;

    @PostConstruct
    public void start() throws Exception {
        BidRequest request = new BidRequest();

        Impression imp = new Impression();
        imp.setBidFloor(1.1);
        imp.setId("abc");
        List<Impression> imps = new ArrayList<Impression>(1);
        imps.add(imp);
        request.setImpressions(imps);

        Geo geo = new Geo();
        geo.setCity("beijing");
        geo.setCountry("china");
        geo.setLat(100.1f);
        geo.setLon(100.1f);

        Device device = new Device();
        device.setMake("apple");
        device.setOs("ios");
        device.setVersion("7.0");
        device.setLang("zh_CN");
        device.setModel("iphone");
        device.setGeo(geo);
        request.setDevice(device);

        // long start = System.currentTimeMillis();

        // for (int i = 0; i < 10000; i ++) {
        // System.out.println(bidService.bid(request).getId());
        System.out.println("SUCESS: got bid response id: " + bidService.bid(request).getId());
        // }

        // System.out.println(">>>>> Total time consumed:" + (System.currentTimeMillis() - start));

        try {
            bidService.throwNPE();
            System.out.println("ERROR: no exception found");
        } catch (NullPointerException e) {
            System.out.println("SUCCESS: caught exception " + e.getClass());
        }

        User user = new User();
        user.setId(1L);
        user.setName("larrypage");
        System.out.println("SUCESS: registered user with id " + anotherUserRestService.registerUser(user).getId());

        System.out.println("SUCESS: got user " + anotherUserRestService.getUser(1L));
    }
}

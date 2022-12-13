package com.jeasy;

import com.alibaba.dubbo.rpc.RpcContext;
import com.jeasy.bid.*;
import com.jeasy.user.User;
import com.jeasy.user.facade.AnotherUserRestService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class DemoAction {

    public static final int NUM_10000 = 10000;
    private BidService bidService;

    private AnotherUserRestService anotherUserRestService;

    public void setBidService(BidService bidService) {
        this.bidService = bidService;
    }

    public void setAnotherUserRestService(AnotherUserRestService anotherUserRestService) {
        this.anotherUserRestService = anotherUserRestService;
    }

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

        long start = System.currentTimeMillis();

        for (int i = 0; i < NUM_10000; i++) {
            System.out.println(bidService.bid(request).getId());
            System.out.println("SUCCESS: got bid response id: " + bidService.bid(request).getId());
        }

        System.out.println(">>>>> Total time consumed:" + (System.currentTimeMillis() - start));

        try {
            bidService.throwNPE();
            System.out.println("ERROR: no exception found");
        } catch (NullPointerException e) {
            System.out.println("SUCCESS: caught exception " + e.getClass());
        }

        User user = new User();
        user.setId(1L);
        user.setName("larrypage");
        System.out.println("SUCCESS: registered user with id " + anotherUserRestService.registerUser(user).getId());

        RpcContext.getContext().setAttachment("clientName", "demo");
        RpcContext.getContext().setAttachment("clientImpl", "dubbox");
        System.out.println("SUCCESS: got user " + anotherUserRestService.getUser(1L));
    }

}

package com.jeasy;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.jeasy.bid.*;
import com.jeasy.user.User;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

    @Override
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        classes.add(BidRequest.class);
        classes.add(BidResponse.class);
        classes.add(Device.class);
        classes.add(Geo.class);
        classes.add(Impression.class);
        classes.add(SeatBid.class);
        classes.add(User.class);
        return classes;
    }
}

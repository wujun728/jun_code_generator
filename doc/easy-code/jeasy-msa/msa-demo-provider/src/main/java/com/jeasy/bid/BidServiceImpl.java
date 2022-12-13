package com.jeasy.bid;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Slf4j
public class BidServiceImpl implements BidService {

    @Override
    public BidResponse bid(BidRequest request) {
        BidResponse response = new BidResponse();

        response.setId("abc");

        SeatBid seatBid = new SeatBid();
        seatBid.setGroup("group");
        seatBid.setSeat("seat");
        List<SeatBid> seatBids = new ArrayList<SeatBid>(1);
        seatBids.add(seatBid);

        response.setSeatBids(seatBids);

        return response;
    }

    @Override
    public void throwNPE() throws NullPointerException {
        throw new NullPointerException();
    }


}

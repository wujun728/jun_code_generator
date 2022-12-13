package com.jeasy.bid;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public interface BidService {

    /**
     * bid
     *
     * @param request
     * @return
     */
    BidResponse bid(BidRequest request);

    /**
     * throwNPE
     *
     * @throws NullPointerException
     */
    void throwNPE() throws NullPointerException;
}

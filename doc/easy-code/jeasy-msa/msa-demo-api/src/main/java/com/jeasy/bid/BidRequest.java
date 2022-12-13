package com.jeasy.bid;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Data
public class BidRequest implements Serializable {

    private String id;

    private Device device;

    private List<Impression> impressions;
}

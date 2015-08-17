package com.alimama.sample.api.controller;

import com.adchina.api.bean.PagingParam;
import com.adchina.api.bean.PagingResult;
import com.adchina.api.bean.Response;
import com.adchina.api.jdbc.paging.Paging;
import com.alimama.sample.api.param.AdvertiserParam;
import com.alimama.sample.api.result.AdvertiserResult;
import com.alimama.sample.api.service.AdvertiserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广告主控制器
 *
 * @author huangyong
 * @since 1.0.0
 */
@RestController
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;

    @RequestMapping(value = "/advertisers", method = RequestMethod.GET)
    public Response listAdvertiser(
        PagingParam pagingParam
    ) {
        int pageNumber = pagingParam.getPagingNumber();
        int pageSize = pagingParam.getPageSize();
        String whereCondition = pagingParam.getWhereCondition("advertiser_name");
        String orderBy = pagingParam.getOrderBy();
        Paging<AdvertiserResult> advertiserResultPaging = advertiserService.getAdvertiserPaging(pageNumber, pageSize, whereCondition, orderBy);
        return new Response().success(new PagingResult<>(advertiserResultPaging));
    }

    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.GET)
    public Response getAdvertiser(
        @PathVariable("id") String advertiserId
    ) {
        AdvertiserResult advertiserResult = advertiserService.getAdvertiser(advertiserId);
        return new Response().success(advertiserResult);
    }

    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.PUT)
    public Response updateAdvertiser(
        @PathVariable("id") String advertiserId,
        @RequestBody @Valid AdvertiserParam advertiserParam
    ) {
        String advertiserName = advertiserParam.getAdvertiserName();
        String description = advertiserParam.getDescription();
        advertiserService.updateAdvertiser(advertiserId, advertiserName, description);
        return new Response().success();
    }

    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.DELETE)
    public Response deleteAdvertiser(
        @PathVariable("id") String advertiserId
    ) {
        advertiserService.deleteAdvertiser(advertiserId);
        return new Response().success();
    }

    @RequestMapping(value = "/advertiser", method = RequestMethod.POST)
    public Response createAdvertiser(
        @RequestBody @Valid AdvertiserParam advertiserParam
    ) {
        String advertiserName = advertiserParam.getAdvertiserName();
        String description = advertiserParam.getDescription();
        advertiserService.createAdvertiser(advertiserName, description);
        return new Response().success();
    }
}

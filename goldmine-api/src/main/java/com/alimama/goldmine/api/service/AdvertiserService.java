package com.alimama.goldmine.api.service;

import com.adchina.api.jdbc.ServiceException;
import com.adchina.api.jdbc.dao.DataAccessor;
import com.adchina.api.jdbc.id.IdGenerator;
import com.adchina.api.jdbc.paging.Paging;
import com.adchina.api.util.DateUtil;
import com.alimama.goldmine.api.constant.Message;
import com.alimama.goldmine.api.result.AdvertiserResult;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 广告主服务
 *
 * @author huangyong
 * @since 1.0.0
 */
@Service
public class AdvertiserService {

    @Autowired
    private DataAccessor dataAccessor;

    @Autowired
    private IdGenerator idGenerator;

    public Paging<AdvertiserResult> getAdvertiserPaging(int pageNumber, int pageSize, String whereCondition, String orderBy) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("whereCondition", whereCondition);
        paramMap.put("orderBy", orderBy);
        return dataAccessor.selectPaging("select:advertiser:paging", paramMap, pageNumber, pageSize);
    }

    public AdvertiserResult getAdvertiser(String advertiserId) {
        AdvertiserResult advertiserResult = dataAccessor.selectOne("select:advertiser:id", advertiserId);
        if (advertiserResult == null) {
            throw new ServiceException(Message.QUERY_FAILURE);
        }
        return advertiserResult;
    }

    @Transactional
    public void updateAdvertiser(String advertiserId, String advertiserName, String description) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", advertiserId);
        paramMap.put("advertiserName", advertiserName);
        paramMap.put("description", description);
        dataAccessor.update("update:advertiser", paramMap);
    }

    @Transactional
    public void deleteAdvertiser(String advertiserId) {
        dataAccessor.update("delete:advertiser", advertiserId);
    }

    @Transactional
    public void createAdvertiser(String advertiserName, String description) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", idGenerator.generateId());
        paramMap.put("advertiserName", advertiserName);
        paramMap.put("description", description);
        paramMap.put("createdTime", DateUtil.getCurrentDateTime());
        dataAccessor.insert("insert:advertiser", paramMap);
    }
}

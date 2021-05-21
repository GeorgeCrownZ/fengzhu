package com.soft.web.expressapi.utils;

import com.google.gson.Gson;
import com.soft.web.constant.ApiInfoConstant;
import com.soft.web.expressapi.core.BaseClient;
import com.soft.web.expressapi.request.BaseRequest;
import com.soft.web.expressapi.request.HttpResult;
import com.soft.web.expressapi.request.QueryTrackReq;
import com.soft.web.expressapi.response.QueryTrackResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

/**
 * 实时查询
 *
 * @Author: api.kuaidi100.com
 * @Date: 2020-07-14 16:27
 */
public class QueryTrack extends BaseClient {

    public String getApiUrl(BaseRequest request) {
        return ApiInfoConstant.QUERY_URL;
    }

    public QueryTrackResp queryTrack(QueryTrackReq queryTrackReq) throws Exception{
        HttpResult httpResult = execute(queryTrackReq);
        if (httpResult.getStatus() == HttpStatus.SC_OK && StringUtils.isNotBlank(httpResult.getBody())){
          return new Gson().fromJson(httpResult.getBody(),QueryTrackResp.class);
        }
        return null;
    }

}

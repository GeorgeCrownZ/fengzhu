package com.soft.web.expressapi.core;

import com.soft.web.expressapi.request.BaseRequest;
import com.soft.web.expressapi.request.HttpResult;
import com.soft.web.expressapi.response.BaseResponse;
import com.soft.web.expressapi.utils.HttpUtils;

/**
 * @Author: api.kuaidi100.com
 * @Date: 2020-11-25 16:02
 */
public abstract class BaseClient implements IBaseClient{

    public HttpResult execute(BaseRequest request) throws Exception{

        return HttpUtils.doPost(getApiUrl(request),request);
    }

    public BaseResponse executeToObject(BaseRequest request) throws Exception{

        return null;
    }

    
    public abstract String getApiUrl(BaseRequest request);

}

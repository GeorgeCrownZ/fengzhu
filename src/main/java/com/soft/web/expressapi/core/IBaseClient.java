package com.soft.web.expressapi.core;

import com.soft.web.expressapi.request.BaseRequest;
import com.soft.web.expressapi.request.HttpResult;
import com.soft.web.expressapi.response.BaseResponse;

/**
 * @Author: api.kuaidi100.com
 * @Date: 2020-11-25 16:09
 */
public interface IBaseClient {

    HttpResult execute(BaseRequest request) throws Exception;

    BaseResponse executeToObject(BaseRequest request) throws Exception;
}

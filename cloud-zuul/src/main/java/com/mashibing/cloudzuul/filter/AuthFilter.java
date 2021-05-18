package com.mashibing.cloudzuul.filter;

import com.mashibing.internalcommon.constant.RedisKeyPrefixConstant;
import com.mashibing.internalcommon.util.JwtInfo;
import com.mashibing.internalcommon.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/5/17 21:59
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Component
@Slf4j
public class AuthFilter extends ZuulFilter {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 拦截类型,4种类型
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 	值越小，越在前
     */
    @Override
    public int filterOrder() {
        return -1;
    }

    /**
     * 	该过滤器是否生效: true生效
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() throws ZuulException {
        System.out.println("auth 拦截");
        // 获取上下文(重要,贯穿所有filter,包括参数)
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            JwtInfo jwtInfo = JwtUtil.parseToken(token);
            if (!Objects.isNull(jwtInfo)) {
                String tokenUserId = jwtInfo.getSubject();
                Long tokenIssueDate = jwtInfo.getIssueDate();
                String redisToken = (String) redisTemplate.opsForValue()
                        .get(RedisKeyPrefixConstant.PASSENGER_LOGIN_CODE_KEY_PREFIX.concat(tokenUserId));
                log.info("redisToken:{}\ntoken:{}", redisToken, token);
            }
        }

        // 不往下走, 还走剩下的过滤器,但是不想后面的服务转发.
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        requestContext.setResponseBody("auth fail");

        return null;
    }
}

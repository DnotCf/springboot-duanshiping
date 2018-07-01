package com.tang.Interceptor;

import com.tang.utils.JsonUtils;
import com.tang.utils.RedisOperator;
import com.tang.utils.ResponseJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class MiniInterceptor implements HandlerInterceptor {

    @Autowired
    public RedisOperator redisOperator;

    public static final String USER_REDIS_SESSION= "user-redis-session";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String userid = request.getHeader("userid");
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(userid) && StringUtils.isNotBlank(token)) {
            String usertoken = redisOperator.get(USER_REDIS_SESSION +":"+ userid);
            if (StringUtils.isEmpty(usertoken) && StringUtils.isBlank(usertoken)) {
                //过期
                returnErrorResponse(httpServletResponse,new ResponseJSONResult().errorTokenMsg("请登陆..."));
                return false;
            } else
            {
                //别地登陆
                if(!usertoken.equals(token)) {
                    returnErrorResponse(httpServletResponse, new ResponseJSONResult().errorTokenMsg("账号被挤出..."));
                    return false;
                }
            }


        }else {
            returnErrorResponse(httpServletResponse,new ResponseJSONResult().errorTokenMsg("请登陆..."));

           return false;
        }

        return true;

    }

    /**
     * 请求controller之后，渲染视图之前
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response, ResponseJSONResult result) throws IOException {

        OutputStream out=null;
        try {

            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();

            }
        }
    }
}

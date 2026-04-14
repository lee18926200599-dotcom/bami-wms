package com.usercenter.server.utils;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.usercenter.server.entity.BaseUserInfo;
import com.usercenter.common.dto.FplUser;
import com.usercenter.common.enums.BusinessSystemEnum;
import com.usercenter.common.enums.SourceTypeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


@Component
@Data
public class UserUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);


    /**
     * web token 超时时间
     */
    @Value("${web.token.expire.time:86400}")
    private int webTokenExpireTime;

    /**
     * app token 超时时间
     */
    @Value("${app.token.expire.time:1296000}")
    private int appTokenExpireTime;

    /**
     * taken生成key
     */
    @Value("${user.token.secretkey:6a4d735b4eb85c4fa0b58545390ba2fb}")
    public String userTokenSecretkey;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRedisUtil userRedisUtil;
    public static final String HEADER_USER_ID = "Userid";
    public static final String HEADER_USER_NAME = "Username";
    public static final String HEADER_GROUP_ID = "Groupid";

    public static Long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String userId=request.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return Long.valueOf(userId);
    }
    /**
     * 获取当前登陆用户名
     */
    public static String getUserName() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String userName=request.getHeader(HEADER_USER_NAME);
        return userName;
    }



    /**
     * 获取当前登陆用户集团id
     */
    public static Long getGroupId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String groupId=request.getHeader(HEADER_GROUP_ID);
        if (StringUtils.isBlank(groupId)) {
            return null;
        }
        return Long.valueOf(groupId);
    }

    /**
     * 生成token
     *
     * @param sourceType     端（电脑端、app端、RF端）
     * @param businessSystem 系统
     * @param userId         用户id
     */
    public TokenContent generateToken(SourceTypeEnum sourceType, BusinessSystemEnum businessSystem, Long userId) {
        TokenContent tokenInfo = new TokenContent();
        //生成token失效时间
        long nowMillis = System.currentTimeMillis();
        Integer ttlMillis = getTtlMillis(sourceType);
        long expMillis = nowMillis + ttlMillis * 1000;
        //生成token
        if (businessSystem != null) {
            Date exp = new Date(expMillis);
            String token = Jwts.builder().setId(userId.toString())
                    .setIssuedAt(new Date(nowMillis))
                    .claim("businessSystem", businessSystem.toString())
                    .claim("sourceType", sourceType.toString())
                    .claim("tokenExpireTime", ttlMillis)
                    .signWith(SignatureAlgorithm.HS512, userTokenSecretkey).compact();
            String key = getKey(userId.intValue(), businessSystem.toString(), token);
            userRedisUtil.setToken(key, token, ttlMillis);
            tokenInfo.setToken(token);
            tokenInfo.setTokenExpireDate(exp);
            tokenInfo.setTokenExpireTime(ttlMillis);
            tokenInfo.setBusinessSystem(businessSystem);
            tokenInfo.setSourceType(sourceType);
            tokenInfo.setId(userId.toString());
        }
        return tokenInfo;
    }

    /**
     * token内容
     */
    public class TokenContent {

        /**
         * token
         */
        private String token;

        /**
         * token 失效时间 yyyy-MM-dd HH:mm:ss
         */
        private Date tokenExpireDate;

        /**
         * token 失效时间（秒）
         */
        private Integer tokenExpireTime;

        /**
         * 登陆的系统
         */
        private BusinessSystemEnum businessSystem;

        /**
         * 端
         */
        private SourceTypeEnum sourceType;

        /**
         * token id
         */
        private String id;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Date getTokenExpireDate() {
            return tokenExpireDate;
        }

        public void setTokenExpireDate(Date tokenExpireDate) {
            this.tokenExpireDate = tokenExpireDate;
        }

        public Integer getTokenExpireTime() {
            return tokenExpireTime;
        }

        public void setTokenExpireTime(Integer tokenExpireTime) {
            this.tokenExpireTime = tokenExpireTime;
        }

        public BusinessSystemEnum getBusinessSystem() {
            return businessSystem;
        }

        public void setBusinessSystem(BusinessSystemEnum businessSystem) {
            this.businessSystem = businessSystem;
        }

        public SourceTypeEnum getSourceType() {
            return sourceType;
        }

        public void setSourceType(SourceTypeEnum sourceType) {
            this.sourceType = sourceType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    /**
     * 获取token有效时长（s）
     *
     * @param sourceType 端（PC/APP/RF）
     * @return
     */
    public Integer getTtlMillis(SourceTypeEnum sourceType) {
        int ttlMillis;
        if (sourceType.getCode() == SourceTypeEnum.PC.getCode()) {
            ttlMillis = webTokenExpireTime;
        } else {
            ttlMillis = appTokenExpireTime;
        }
        return ttlMillis;
    }

    private String getKey(Integer userId, String appKey, String token) {
        StringBuffer key = new StringBuffer();
        key.append(userId.toString());
        key.append(":");
        key.append(appKey);
        key.append(":");
        key.append(token);
        return key.toString();
    }

    /**
     * 生成6位随机数
     *
     * @return
     */
    public static String getCode() {
        double rad = Math.random();
        int code = (int) (rad * 899999 + 100000);
        return code + "";
    }

    public static FplUser convertToUser(BaseUserInfo baseUserInfo) {
        FplUser fplUser = new FplUser();
        BeanUtils.copyProperties(baseUserInfo, fplUser);
        fplUser.setUserAuthId(baseUserInfo.getUserId());
        return fplUser;
    }

    public static List<FplUser> convertToUserList(List<BaseUserInfo> baseUserInfoList) {
        List<FplUser> result = Lists.newArrayList();
        for (BaseUserInfo baseUserInfo : baseUserInfoList) {
            FplUser fplUserInfoDTO = convertToUser(baseUserInfo);
            result.add(fplUserInfoDTO);
        }
        return result;
    }

    /**
     * 根据token获取token信息
     *
     * @return token信息
     */
    public TokenContent getTokenContent(String token) {
        Claims claims = Jwts.parser().setSigningKey(userTokenSecretkey).parseClaimsJws(token).getBody();
        TokenContent tokenContent = new TokenContent();
        tokenContent.setId(claims.getId());
        Object businessSystem = claims.get("businessSystem");
        Object sourceType = claims.get("sourceType");
        Object tokenExpireTime = claims.get("tokenExpireTime");
        if (businessSystem != null) {
            tokenContent.setBusinessSystem(BusinessSystemEnum.parser((String) businessSystem));
        }
        if (sourceType != null) {
            tokenContent.setSourceType(SourceTypeEnum.parser((String) sourceType));
        }
        if (tokenExpireTime != null) {
            tokenContent.setTokenExpireTime((Integer) tokenExpireTime);
        }
        return tokenContent;
    }

    /**
     * 获取用户下的获取token列表
     *
     * @param userId
     * @return
     */
    public Map<String, String> getUserTokenMap(String userId) {
        TreeSet<String> userKeys = userRedisUtil.getUserKeys(userId);
        Map<String, String> tokenMap = Maps.newHashMap();
        for (String key : userKeys) {
            tokenMap.put((String) userRedisUtil.get(key), key);
        }
        return tokenMap;
    }

    /**
     * 转换
     */
    public static void convert(BaseUserInfo baseUserInfo, FplUser fplUser) {
        BeanUtil.copyProperties(baseUserInfo, fplUser);
        fplUser.setUserAuthId(baseUserInfo.getUserId());
    }
}

package org.yunqi.common.utils.constant;

import java.io.Serializable;

/**
 * 默认配置常量信息
 *
 * @author zhangzhiwei
 */
public class Const implements Serializable {

    /**
     * 默认分隔符
     */
    public static final String SEPARATOR =",";

    /**
     * 查询数据权限递归次数,可以通过继承这个类修改
     */
    public static final int AUTH_DATA_RECURSION_NUM = 20;

    /**
     * 默认编码
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * sql最大查询条数限制,以及字段数量限制
     */
    public static final Integer QUERY_MAX_SIZE = 100;

    /**
     * 用户token的最长过期时间
     */
    public static final Integer MAX_USER_EXIST_TIME = 3600 * 24 * 7;

    /**
     * 批量保存的条数
     */
    public static final int BATCH_SAVE_SIZE = 200;



}

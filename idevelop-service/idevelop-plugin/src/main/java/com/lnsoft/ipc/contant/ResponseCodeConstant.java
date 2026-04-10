package com.lnsoft.ipc.contant;

/**
 * 响应状态码常量类
 */
public final class ResponseCodeConstant {


    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;


    /**
     * 客户端错误（通用）
     */
    public static final int CLIENT_ERROR = 400;

    /**
     * 无效的方法
     */
    public static final int INVALID_METHOD = 401;

    /**
     * 请求数据为空
     */
    public static final int REQUEST_DATA_EMPTY = 402;

    /**
     * 数据解密失败
     */
    public static final int DATA_DECRYPTION_FAILED = 403;

    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 405;

    // --- 5xx Server Error ---

    /**
     * 服务器错误（通用）
     */
    public static final int SERVER_ERROR = 500;


    /**
     * 对时错误
     */
    public static final int TIME_SYNC_ERROR = 10005;


}

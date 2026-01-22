package com.lnsoft.ipc.contant;

/**
 * 操作类型常量类
 * <p>
 * 统一管理系统中所有业务操作的类型，避免硬编码字符串导致的错误，
 * 提高代码可读性和可维护性。
 * </p>
 */
public final class OperationTypeConstant {

    /**
     * 操作类型：添加
     */
    public static final String ADD = "add";

    /**
     * 操作类型：删除
     */
    public static final String DELETE = "delete";

    /**
     * 操作类型：更新
     */
    public static final String UPDATE = "update";
}

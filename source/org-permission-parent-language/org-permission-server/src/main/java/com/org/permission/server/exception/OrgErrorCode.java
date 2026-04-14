package com.org.permission.server.exception;

/**
 * 组织错误码
 */
public class OrgErrorCode {
	/**
	 * 请求参数错误
	 */
	public static final int REQ_PARAM_ERROR_CODE = 1001;

	/**
	 * 用户权限错误
	 */
	public static final int USER_PERMISION_ERROR_CODE = 2000;
	/**
	 * 用户权限不足
	 */
	public static final int USER_PERMISION_NOT_ENOUGH_ERROR_CODE = 2001;


	/**
	 * 数据状态异常
	 */
	public static final int DATA_STATE_ERROR_CODE = 3000;

	/**
	 * 数据不存在
	 */
	public static final int DATA_NOT_EXIST_ERROR_CODE = 3001;

	/**
	 * 当前状态不允许启用
	 */
	public static final int STATE_CANNOT_OP_ERROR_CODE = 3002;

	/**
	 * 未绑定客商信息
	 */
	public static final int UNBINDING_CUSTER_CANNOT_ENABLE_ERROR_CODE = 3003;

	/**
	 * 客商已经绑定集团
	 */
	public static final int CUSTER_ALREADY_BINDING_GROUP_ERROR_CODE = 3004;

	/**
	 * 已起用不允许删除
	 */
	public static final int ENABLED_STATE_CAN_NOT_DELETE_ERROR_CODE = 3006;

	/**
	 * 已删除不可再操作
	 */
	public static final int DELETED_CAN_NOT_OPERATE_ERROR_CODE = 3007;
	/**
	 * 未启用业务单元不可停用
	 */
	public static final int UNENABLE_BIZ_UNIT_CAN_NOT_STOP_ERROR_CODE = 3008;
	/**
	 * 根业务单元不允许删除
	 */
	public static final int MAIN_BIZ_UNIT_CAN_NOT_DELETE_ERROR_CODE = 3009;
	/**
	 * 客户状态不可绑定集团
	 */
	public static final int CUSTER_STATE_ERROR_CODE = 3010;
	/**
	 * 默认采销委托关系冲突
	 */
	public static final int DEFAULT_ENTRUST_CONFLIC_ERROR_CODE = 3011;
	/**
	 * 启用采销委托关系冲突
	 */
	public static final int ENABLE_ENTRUST_CONFLIC_ERROR_CODE = 3012;
	/**
	 * 启用采销委托关系冲突
	 */
	public static final int BUSINESS_TYPE_NOT_EXIST_ERROR_CODE = 3013;
	/**
	 * 同一业务单元下部门名称冲突
	 */
	public static final int DEP_NAME_CONFLICT_ERROR_CODE = 3014;
	/**
	 * 已引用不允许删除
	 */
	public static final int QUOTED_NOT_CAN_DELETE_ERROR_CODE = 3015;

	/**
	 * 有人员的部门不允许删除
	 */
	public static final int HAVE_STAFF_DEP_NOT_CAN_DELETE_ERROR_CODE = 3016;

	/**
	 * 命名冲突
	 */
	public static final int NAME_CONFLICT_ERROR_CODE = 3017;

	/**
	 * 组织系统错误
	 */
	public static final int ORG_SYSTEM_ERROR_CODE = 5000;

	/**
	 * 客户系统错误
	 */
	public static final int CRM_SYSTEM_ERROR_CODE = 6001;

	/**
	 * 字典系统错误
	 */
	public static final int DIC_SYSTEM_ERROR_CODE = 7001;

	/**
	 * 用户系统错误
	 */
	public static final int USER_SYSTEM_ERROR_CODE = 8001;

	/**
	 * 编码系统错误
	 */
	public static final int CODE_SYSTEM_ERROR_CODE = 8001;

	/**
	 * 仓储系统错误
	 */
	public static final int WMS_SYSTEM_ERROR_CODE = 9001;

	/**
	 * 支付系统错误
	 */
	public static final int PAY_SYSTEM_ERROR_CODE = 1001;

	/**
	 * 开发平台错误
	 */
	public static final int OPEN_SYSTEM_ERROR_CODE = 2001;
}

package com.landicorp.nuoxin.net;


/**
 * 网络访问Action定义
 * @author tanlp
 */
public enum NetWorkAction
{
	/**
	 * 前后台网络接口【入口模块】--用户登录
	 */
	APPLY_PK("pk", "applyPk"),//发送pk
	SAVE_PK("pk", "savePk"),//保存pk
	GET_MEMBER_LIST_BY_DEPT("pk", "getMemberListByDept"),//部门同事
	GET_MEMBER_LIST_RANK_BY_USER_ID("pk", "getMemberListRankByUserId"),//推荐同事
	SEARCH_USER("pk", "searchUser"),//搜索同事
	QUERY_PK_INFO_LIST("pk", "queryPkInfoList"),//pk挑战列表
	QUERY_INVITE_PK_INFO_LIST("pk", "queryInvitePkInfoList"),//邀请中pk列表
	QUERY_HISTORY_PK_INFO_LIST("pk", "queryHistoryPkInfoList"),//pk历史列表
	CANCLE_INVITE("pk", "cancleInvite"),//取消邀请
	REFUSE_INVITE("pk", "refuseInvite"),//拒绝邀请
	ACCEPT_INVITE("pk", "acceptInvite"),//接受邀请
	MEMBER_INFO("pk", "memberInfo") //用户信息
	;
	
	/**
	 * 请求模块名称
	 */
	public final String modelName;

	/**
	 * 请求接口名称
	 */
	public final String functionName;

	private NetWorkAction(String modelName, String functionName)
	{
		this.modelName = modelName;
		this.functionName = functionName;
	}
}

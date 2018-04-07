package com.hsbank.util.project.provider;

import android.content.Context;

import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;

/**
 * 接口
 * @author wuyuan.xie
 * CreateDate 2007-09-11
 */
public interface IBaseProvider {
	/**
	 * 发送短信验证码
	 * @param context
	 * @param mobile
	 * @param callback
	 */
	public void sendSmsAuthCode(Context context, String mobile, ApiCallback callback);

	/**
	 * 指定帐号是否已注册
	 * @param context
	 * @param account
	 * @param callback
	 */
	public void hasRegistered(Context context, String account, ApiCallback callback);

	/**
	 * 用户注册
	 * @param context
	 * @param account			帐号
	 * @param password			密码
	 * @param smsAuthCode		短信验证码
	 * @param invitationCode	邀请码
	 * @param registerChannel	注册渠道代码
	 * @param callback			回调方法
	 */
	public void register(Context context, String account, String password, String smsAuthCode, String invitationCode, String registerChannel, ApiCallback callback);

	/**
	 * 用户登录：帐号、密码登录
	 * @param context
	 * @param account
	 * @param password
	 * @param callback
	 */
	public void loginByPassword(Context context, String account, String password, ApiCallback callback);

	/**
	 * 用户登录：帐号、短信验证号登录
	 * @param context
	 * @param account
	 * @param smsAuthCode
	 * @param callback
	 */
	public void loginBySmsAuthCode(Context context, String account, String smsAuthCode, ApiCallback callback);

	/**
	 * 用户登出
	 * @param context
	 * @param token
	 * @param callback
	 */
	public void logout(Context context, String token, ApiCallback callback);

	/**
	 * 修改密码（用户已登录）
	 * @param context
	 * @param token
	 * @param oldPassword
	 * @param newPassword
	 * @param callback
	 */
	public void changePassword(Context context, String token, String oldPassword, String newPassword, ApiCallback callback);

	/**
	 * 重置密码（用户未登录）
	 * @param context
	 * @param account
	 * @param newPassword
	 * @param smsAuthCode
	 * @param callback
	 */
	public void resetPassword(Context context, String account, String newPassword, String smsAuthCode, ApiCallback callback);

	/**
	 * 用户签到
	 * @param context
	 * @param token
	 * @param callback
	 */
	public void sign(Context context, String token, ApiCallback callback);

	/**
	 * 得到当前用户的相关信息
	 * @param context
	 * @param token
	 * @param callback
	 */
	public void my(Context context, String token, ApiCallback callback);

	/**
	 * 更新头像
	 * @param context
	 * @param token
	 * @param avatarContent
	 * @param callback
	 * */
	public void updateAvatar(Context context, String token, String avatarContent, ApiCallback callback);

	/**
	 * 更新昵称
	 * @param context
	 * @param token
	 * @param nickname
	 * @param callback
	 */
	public void updateNickname(Context context, String token, String nickname, ApiCallback callback);

	/**
	 * 更新帐号
	 * @param context
	 * @param token
	 * @param account
	 * @param callback
	 */
	public void updateAccount(Context context, String token, String account, ApiCallback callback);

	/**
	 * @param context
	 * 更新手机号码
	 * @param token
	 * @param mobile
	 * @param callback
	 */
	public void updateMobile(Context context, String token, String mobile, ApiCallback callback);

	/**
	 * 更新邮箱
	 * @param context
	 * @param token
	 * @param email
	 * @param callback
	 */
	public void updateEmail(Context context, String token, String email, ApiCallback callback);

	/**
	 * 版本自动更新检查
	 * @param context
	 * @param callback
	 */
	public void checkUpdate(Context context, ApiCallback callback);

	/**
	 * 手机号码是否正确
	 * @param mobile
	 * @param callback
	 */
	public void isMobile(Context context, String mobile, ApiCallback callback);

	/**
	 * 得到(我的)收件人地址列表：分页
	 * @param context
	 * @param token
	 * @param pageSize
	 * @param pageNumber
	 * @param callback
	 */
	public void getMyRecipientAddressPageList(Context context, String token, int pageSize, int pageNumber, ApiCallback callback);

	/**
	 * 得到(我的)收件人地址列表：不分页
	 * @param context
	 * @param token
	 * @param callback
	 */
	public void getMyRecipientAddressList(Context context, String token, ApiCallback callback);

	/**
	 * 新增(我的)收件人地址
	 * @param context
	 * @param token			当前用户登录标识
	 * @param showName		显示名称
	 * @param mobile		手机号码
	 * @param address		地址
	 * @param postcode		邮编
	 * @param isDefault		是否保存为默认地址
	 * @param callback		回调方法
	 */
	public void addMyRecipientAddress(Context context, String token, String showName, String mobile, String address, String postcode, String isDefault, ApiCallback callback);

	/**
	 * 编辑(我的)收件人地址
	 * @param context
	 * @param token			当前用户登录标识
	 * @param id			记录Id
	 * @param showName		显示名称
	 * @param mobile		手机号码
	 * @param address		地址
	 * @param postcode		邮编
	 * @param isDefault		是否保存为默认地址
	 * @param callback		回调方法
	 */
	public void editMyRecipientAddress(Context context, String token, String id, String showName, String mobile, String address, String postcode, String isDefault, ApiCallback callback);

	/**
	 * 删除(我的)收件人地址
	 * @param context
	 * @param token			当前用户登录标识
	 * @param id			记录Id
	 * @param callback
	 */
	public void deleteMyRecipientAddress(Context context, String token, String id, ApiCallback callback);
}

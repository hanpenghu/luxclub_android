package com.hsbank.xgpush;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import de.greenrobot.event.EventBus;

public class XGMesageReceiver extends XGPushBaseReceiver {

	// 通知被展示触发的结果，可以在此保存APP收到的通知
	@Override
	public void onNotifactionShowedResult(Context context,
			XGPushShowedResult notifiShowedRlt) {
//		if (context == null || notifiShowedRlt == null) {
//			return;
//		}
		// 具体操作(待扩展)
		// 发送消息通知到到相关页面
	}

	/**
	 * 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
	 * 这个动作可以在activity的onResume也能监听
	 * 获取自定义key-value
	 * @param context
	 * @param message
	 */
	@Override
	public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}
		String json = message.getCustomContent();
		if (TextUtils.isEmpty(json)) {
			return;
		}
		// 发送消息内容到主页
		EventBus.getDefault().postSticky(new TPushEvent(TPushEvent.COMMAND_TPUSH, json));
	}

	/**
	 * 注册回调结果
	 * @param context
	 * @param errorCode
	 */
	@Override
	public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult message) {
		if (context == null || message == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = message + "注册成功";
			// 在这里拿token
			String token = message.getToken();
		} else {
			text = message + "注册失败，错误码：" + errorCode;
		}
		// 具体操作(待扩展)
	}

	/**
	 * 反注册回调结果
	 * @param context
	 * @param errorCode
     */
	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "反注册成功";
		} else {
			text = "反注册失败" + errorCode;
		}
		// 具体操作(待扩展)
	}

	/**
	 * 设置标签结果
	 * @param context
	 * @param errorCode
	 * @param tagName
     */
	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"设置成功";
		} else {
			text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
		}
		// 具体操作(待扩展)
	}

	/**
	 * 删除标签结果
	 * @param context
	 * @param errorCode
	 * @param tagName
     */
	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"删除成功";
		} else {
			text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
		}
		// 具体操作(待扩展)
	}

	/**
	 * 收到的透传消息
	 * 可获取自定义key-value，json格式
	 * @param context
	 * @param message
     */
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		String customContent = message.getCustomContent();
		// 自主处理消息的过程...
	}
}

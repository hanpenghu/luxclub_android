package com.hsbank.util.android.fragment.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsbank.util.android.util.helper.ViewFindHelper;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import de.greenrobot.event.EventBus;

/**
 * 基础Fragment
 * @author Arthur.Xie
 * 2016-01-24
 */
public abstract class BaseFragment extends RxFragment {
	/**视图辅助类*/
	protected ViewFindHelper mViewHelper;
	/**当前类的Activity的上下文对象，为了简化书写*/
	protected Context mContext;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
		//注册EventBus
		EventBus.getDefault().register(this);
	}

	/**
	 * EventBus消息处理器
	 * 注意：此方法必须以onEvent开头的、非静态的、public权限，否则打包后会出现崩溃
	 * @param event			事件
	 */
	public void onEventMainThread(BaseProjectEvent event) {
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//注销EventBus
		EventBus.getDefault().unregister(this);
		//垃圾回收
		System.gc();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mViewHelper = new ViewFindHelper(inflater, container, getLayoutId());
		return mViewHelper.getContentView();
	}

	/**
	 * 得到布局文件Id
	 * @return 布局文件Id
	 */
	protected abstract int getLayoutId();

	@Override
	public void onPause() {
		super.onPause();
		//页面跳转时候取消Toast对象的显示
//		AlertUtil.getInstance().cancel();
	}

	@Override
	public void onStop() {
		super.onStop();
		//页面跳转时候取消Toast对象的显示
//		AlertUtil.getInstance().cancel();
	}
}

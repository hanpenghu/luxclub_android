package com.hsbank.util.android.fragment.util;

import com.baidu.mobstat.StatService;

/**
 * 添加了百度统计功能的基础视图
 * @author Arthur.Xie
 * 2016-01-24
 */
public abstract class BaiduStatFragment extends BaseFragment {
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
}

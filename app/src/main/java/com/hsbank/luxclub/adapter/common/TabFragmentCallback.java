package com.hsbank.luxclub.adapter.common;

import android.support.v4.app.Fragment;

/**
 * Author:      chen_liuchun
 * Date:        2016/5/26
 * Description: TabLayout指示器通用Fragment传参接口
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------
 */
public interface TabFragmentCallback {
    /**
     * 返回对应页面的Fragment
     * @param position 页面索引
     * @return Fragment实例
     */
    Fragment getFragment(int position);
}

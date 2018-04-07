util-push-xg
------------

# 移植说明
- 直接拷贝本module即可；
- 必须更改XGRemoteService的intent-filter为包名开头
- 必须更改XG_V2_ACCESS_ID和XG_V2_ACCESS_KEY
- PushUtil的accountID是账户唯一的区分标志；

# 升级提示：
1 使用Xg_sdk_vxxx.jar替换旧版本
2 复制libs目录下的文件，替换旧的，注意：通常只需要添加armeabi目录即可，若您的工程还有其它平台的.so，请选择对应平台添加上去。
3 若您的工程没有使用MTA（腾讯云分析），删除mid-sdk-2.10.jar
4 参考AndroidManifest.xml配置相关内容
5 XGPushActivity和用户自定义的MessageReceiver的android:exported建议设置为"false" ，具体请参考demo中的AndroidManifest.xml

# 相关链接
[10分钟快速指南，请先参考本指南接入]
(http://developer.xg.qq.com/index.php/Android_SDK%E5%BF%AB%E9%80%9F%E6%8C%87%E5%8D%97)

[官方网站]
(http://xg.qq.com)

[开发者中心]
(http://developer.xg.qq.com/index.php/Main_Page)

[常见问题]
(http://developer.xg.qq.com/index.php/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)

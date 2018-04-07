package com.hsbank.luxclub.util.check_update;

/**
 * Author:      chen_liuchun
 * Date:        2016/6/17
 * Description: 应用更新信息实体
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class CheckUpdateEntity {

    // 是否需要强制更新（0是，其它不是）
    private String needUpdate;
    //  是否需要强制更新（0是，其它不是）
    private String needForcedUpdate;
    // 待更新的apk文件url
    private String url;
    // 待更新App版本
    private String version;
    // 待更新App版本说明
    private String versionInfo;
    // 待更新App文件大小（byte）
    private String androidAppSize;

    public boolean getNeedUpdate() {
        int i;
        try {
            i = Integer.parseInt(needUpdate);
        } catch (NumberFormatException e) {
            return false;
        }
        return i == 0;
    }

    public void setNeedUpdate(String needUpdate) {
        this.needUpdate = needUpdate;
    }

    public boolean getNeedForcedUpdate() {
        int i;
        try {
            i = Integer.parseInt(needForcedUpdate);
        } catch (NumberFormatException e) {
            return false;
        }
        return i == 0;
    }

    public void setNeedForcedUpdate(String needForcedUpdate) {
        this.needForcedUpdate = needForcedUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getAndroidAppSize() {
        return androidAppSize;
    }

    public void setAndroidAppSize(String androidAppSize) {
        this.androidAppSize = androidAppSize;
    }

}

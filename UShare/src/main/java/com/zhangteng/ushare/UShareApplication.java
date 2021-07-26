package com.zhangteng.ushare;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zhangteng.base.base.BaseApplication;
import com.zhangteng.ushare.constants.Constant;

public class UShareApplication extends BaseApplication {

    @Override
    public void initModuleApp(Application application) {
        UMConfigure.init(this, "5a12384aa40fa3551f0001d1"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        // 微信设置
        PlatformConfig.setWeixin(Constant.WX_APPID, Constant.WX_SECRET);
        PlatformConfig.setWXFileProvider(Constant.APP_ID + ".FileProvider");
        // QQ设置
        PlatformConfig.setQQZone(Constant.QQZONE_APPID, Constant.QQZONE_SECRET);
        PlatformConfig.setQQFileProvider(Constant.APP_ID + ".FileProvider");
        // 新浪微博设置
        PlatformConfig.setSinaWeibo(Constant.SINAWEIBO_APPID, Constant.SINAWEIBO_SECRET, "http://mobile.umeng.com/social");
        PlatformConfig.setSinaFileProvider(Constant.APP_ID + ".FileProvider");
        //钉钉设置
        PlatformConfig.setDing(Constant.DINGDING_APPID);
        PlatformConfig.setDingFileProvider(Constant.APP_ID + ".FileProvider");
        // 企业微信设置
        PlatformConfig.setWXWork(Constant.WX_WORK_APPID, Constant.WX_WORK_SECRET, Constant.WX_WORK_AGENTID, Constant.WX_WORK_SCHEMA);
        PlatformConfig.setWXWorkFileProvider(Constant.APP_ID + ".FileProvider");
        //支付宝设置
        PlatformConfig.setAlipay(Constant.ALIPAY_APPID);
    }

    @Override
    public void initModuleData(Application application) {

    }
}

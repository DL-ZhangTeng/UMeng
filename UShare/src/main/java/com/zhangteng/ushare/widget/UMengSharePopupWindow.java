package com.zhangteng.ushare.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * εηεδΊ«
 * Created by swing on 2019/8/21 0021.
 */
public class UMengSharePopupWindow extends SharePopupWindow {
    private WeakReference<Activity> activity;
    private String title;
    private String url;
    private String description;
    private String thumbImage;
    private UMShareListener mShareListener;

    public UMengSharePopupWindow(Activity activity) {
        super(activity);

        this.activity = new WeakReference<>(activity);
        mShareListener = new CustomShareListener(activity);
        setOnDismissListener(() -> dismiss(UMengSharePopupWindow.this.activity.get()));
    }


    public UMengSharePopupWindow(Activity activity, String title, String url, String description) {
        super(activity);
        this.activity = new WeakReference<>(activity);
        this.title = title;
        this.url = url;
        this.description = description;
        mShareListener = new CustomShareListener(activity);
        setOnItemClickListener((view, position) -> share(position));
        setOnDismissListener(() -> dismiss(UMengSharePopupWindow.this.activity.get()));
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public void share(int position) {
        dismiss();
        SHARE_MEDIA shareMedia;
        if (position == QQ) {
            if (isQQClientAvailable(this.activity.get())) {
                shareMedia = SHARE_MEDIA.QQ;
            } else {
                Toast.makeText(activity.get(), "ζͺε?θ£QQ", Toast.LENGTH_SHORT).show();
                return;
            }

        } else if (position == QQKONGJIAN) {
            if (isQQClientAvailable(this.activity.get())) {
                shareMedia = SHARE_MEDIA.QZONE;
            } else {
                Toast.makeText(activity.get(), "ζͺε?θ£QQ", Toast.LENGTH_SHORT).show();
                return;
            }

        } else if (position == WX) {
            if (isWeixinAvilible(this.activity.get())) {
                shareMedia = SHARE_MEDIA.WEIXIN;
            } else {
                Toast.makeText(activity.get(), "ζͺε?θ£εΎ?δΏ‘", Toast.LENGTH_SHORT).show();
                return;
            }

        } else if (position == WXPENGYOUQUAN) {
            if (isWeixinAvilible(this.activity.get())) {
                shareMedia = SHARE_MEDIA.WEIXIN_CIRCLE;
            } else {
                Toast.makeText(activity.get(), "ζͺε?θ£εΎ?δΏ‘", Toast.LENGTH_SHORT).show();
                return;
            }

        } else if (position == WB) {
            shareMedia = SHARE_MEDIA.SINA;
        } else if (position == DD) {
            shareMedia = SHARE_MEDIA.DINGTALK;
        } else if (position == ALIPAY) {

            if (isAliPayClientAvailable(this.activity.get())) {
                shareMedia = SHARE_MEDIA.ALIPAY;
            } else {
                Toast.makeText(activity.get(), "ζͺε?θ£ζ―δ»ε?", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            shareMedia = SHARE_MEDIA.SINA;
        }
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//ζ ι’
        if (!TextUtils.isEmpty(thumbImage)) {
            UMImage image = new UMImage(activity.get(), thumbImage);
            web.setThumb(image);
        } else {
            web.setThumb(new UMImage(this.activity.get(), android.R.mipmap.sym_def_app_icon));  //ηΌ©η₯εΎ
        }
        web.setDescription(description);//ζθΏ°
        new ShareAction(this.activity.get())
                .setPlatform(shareMedia)//δΌ ε₯εΉ³ε°
                .withMedia(web)
                .setCallback(mShareListener)
                .share();

    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// θ·εpackagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// θ·εζζε·²ε?θ£η¨εΊηεδΏ‘ζ―
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isAliPayClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.eg.android.AlipayGphone")) {
                    return true;
                }
            }
        }
        return false;
    }


    private class CustomShareListener implements UMShareListener {
        private WeakReference<Activity> mActivity;

        private CustomShareListener(Activity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), " ζΆθζεε¦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), " εδΊ«ζεε¦", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST
                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), " εδΊ«ε€±θ΄₯ε¦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            Toast.makeText(mActivity.get(), " εδΊ«εζΆδΊ", Toast.LENGTH_SHORT).show();
        }
    }
}

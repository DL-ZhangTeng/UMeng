package com.zhangteng.ushare.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import com.alipay.sdk.app.AuthTask
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.zhangteng.base.base.BaseMvpActivity
import com.zhangteng.base.utils.LogUtils
import com.zhangteng.ushare.mvp.contract.ThirdPartyLoginContract.*
import com.zhangteng.ushare.utils.AuthResult

/**
 * @description: 三方登录Activity
 * @author: Swing
 * @date: 2021/7/26
 */
abstract class ThirdPartyLoginActivity<V : IThirdPartyLoginView, M : IThirdPartyLoginModel, P : IThirdPartyLoginPresenter<V, M>> :
    BaseMvpActivity<V, M, P>(), IThirdPartyLoginView {
    private val tag = this.javaClass.simpleName
    protected var mark: String? = null
    protected var code: String? = null

    @SuppressLint("HandlerLeak")
    protected val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 700) {
                val authResult = AuthResult(msg.obj as Map<String?, String?>, true)
                val resultStatus: String = authResult.getResultStatus()
                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(
                        resultStatus,
                        "9000"
                    ) && TextUtils.equals(authResult.getResultCode(), "200")
                ) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    showToast("第三方授权成功")
                    authResult.getAuthCode()
                    //todo 将此code给后端，用来请求用户数据后返回前端
                }
            }
        }
    }

    /**
     * 支付宝账户授权业务
     */
    protected open fun authorization2(authInfo: String?) {
        val authRunnable = Runnable {

            // 构造AuthTask 对象
            val authTask = AuthTask(this@ThirdPartyLoginActivity)
            // 调用授权接口，获取授权结果
            val result: Map<String, String> =
                authTask.authV2(authInfo, true)
            val msg = Message()
            msg.what = 700
            msg.obj = result
            handler.sendMessage(msg)
        }
        // 必须异步调用
        val authThread = Thread(authRunnable)
        authThread.start()
    }

    /**
     * 授权
     *
     * @param share_media
     */
    protected open fun authorization(share_media: SHARE_MEDIA) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, object : UMAuthListener {
            override fun onStart(share_media: SHARE_MEDIA) {
                Log.d(tag, "onStart " + "授权开始")
            }

            override fun onComplete(share_media: SHARE_MEDIA, i: Int, map: Map<String, String>) {
                Log.d(tag, "onComplete " + "授权完成")

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                val uid = map["uid"]
                val openId = map["openid"]
                val name = map["name"]
                val token = map["accessToken"]
                showToast("$name：授权成功")
                if (share_media == SHARE_MEDIA.WEIXIN) {
                    mark = "wx"
                    code = openId
                } else if (share_media == SHARE_MEDIA.QQ) {
                    mark = "qq"
                    code = openId
                } else if (share_media == SHARE_MEDIA.SINA) {
                    mark = "wb"
                    code = uid
                } else if (share_media == SHARE_MEDIA.ALIPAY) {
                    mark = "zfb"
                    code = uid
                }
                LogUtils.e("第三方信息", "uid=$uid")
                if (!TextUtils.isEmpty(uid)) {
                    mPresenter?.otherLogin(mark, code, token)
                } else {
                    showToast("第三方信息获取失败")
                }
                if (share_media == SHARE_MEDIA.WEIXIN) {
                    UMShareAPI.get(this@ThirdPartyLoginActivity)
                        .deleteOauth(this@ThirdPartyLoginActivity, SHARE_MEDIA.WEIXIN, null)
                } else if (share_media == SHARE_MEDIA.QQ) {
                    UMShareAPI.get(this@ThirdPartyLoginActivity)
                        .deleteOauth(this@ThirdPartyLoginActivity, SHARE_MEDIA.QQ, null)
                } else if (share_media == SHARE_MEDIA.SINA) {
                    UMShareAPI.get(this@ThirdPartyLoginActivity)
                        .deleteOauth(this@ThirdPartyLoginActivity, SHARE_MEDIA.SINA, null)
                } else if (share_media == SHARE_MEDIA.ALIPAY) {
                    UMShareAPI.get(this@ThirdPartyLoginActivity)
                        .deleteOauth(this@ThirdPartyLoginActivity, SHARE_MEDIA.ALIPAY, null)
                }
            }

            override fun onError(share_media: SHARE_MEDIA, i: Int, throwable: Throwable) {
                Log.d(tag, "onError " + "授权失败")
            }

            override fun onCancel(share_media: SHARE_MEDIA, i: Int) {
                Log.d(tag, "onCancel " + "授权取消")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)

    }
}
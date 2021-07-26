package com.zhangteng.ushare.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhangteng.base.base.BasePopupWindow;
import com.zhangteng.ushare.R;

/**
 * Created by swing on 2018/9/6.
 */
public class SharePopupWindow extends BasePopupWindow {
    private OnItemClickListener onItemClickListener;

    protected static final int QQ = 0;
    protected static final int QQKONGJIAN = 1;
    public static final int WX = 2;
    public static final int WXPENGYOUQUAN = 3;
    protected static final int WB = 4;
    protected static final int DD = 5;
    protected static final int ALIPAY = 6;

    public SharePopupWindow(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getSelfTitleView() {
        return 0;
    }

    @Override
    public int getSelfContentView() {
        return R.layout.common_popupwindow_share_content;
    }

    @Override
    public int getSelfButtonView() {
        return 0;
    }

    @Override
    public void initView(View view) {
        setDropUp();
        getParent().setBackgroundResource(R.drawable.wallet_popupwindow_share_shape);
        LinearLayout walletShareQq = view.findViewById(R.id.wallet_share_qq);
        LinearLayout walletShareQqkongjian = view.findViewById(R.id.wallet_share_qqkongjian);
        LinearLayout walletShareWeixin = view.findViewById(R.id.wallet_share_weixin);
        LinearLayout walletShareWeixinpengyouquan = view.findViewById(R.id.wallet_share_weixinpengyouquan);
        LinearLayout walletShareWeibo = view.findViewById(R.id.wallet_share_weibo);
        LinearLayout walletShareDingding = view.findViewById(R.id.wallet_share_dingding);
        LinearLayout walletShareZhifubao = view.findViewById(R.id.wallet_share_zhifubao);
        TextView commonShareCancle = view.findViewById(R.id.common_share_cancle);

        walletShareQq.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, QQ);
            }
        });
        walletShareQqkongjian.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, QQKONGJIAN);
            }
        });
        walletShareWeixin.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, WX);
            }
        });
        walletShareWeixinpengyouquan.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, WXPENGYOUQUAN);
            }
        });
        walletShareWeibo.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, WB);
            }
        });
        walletShareDingding.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, DD);
            }
        });
        walletShareZhifubao.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, ALIPAY);
            }
        });
    }

    /**
     * 从下往上弹出
     *
     * @param activity 用于改变窗口背景
     * @param parent   相对于parent的位置
     * @param x        相对于parent的位置
     * @param y        相对于parent的位置
     */
    public void showAtLocation(Activity activity, View parent, int gravity, int x, int y) {
        showBlackWindowBackground(activity);
        if (!isShowing())
            showAtLocation(parent, gravity, x, y);
    }

    /**
     * 关闭
     *
     * @param activity 用于改变窗口背景
     */
    public void dismiss(Activity activity) {
        if (isShowing())
            dismiss();
        dismissBlackWindowBackground(activity);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface SuccessShare {
        void OnSuccessShare();
    }

    public interface OnDelectShareClickListener {
        void onDelectShareClick();
    }
}

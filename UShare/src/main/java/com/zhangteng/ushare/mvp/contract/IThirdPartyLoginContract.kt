package com.zhangteng.ushare.mvp.contract

import com.zhangteng.base.mvp.base.IModel
import com.zhangteng.base.mvp.base.IPresenter
import com.zhangteng.base.mvp.base.IView

interface ThirdPartyLoginContract {
    interface IThirdPartyLoginView : IView {
        fun otherLoginSuccess(mark: String?, code: String?, token: String?)
    }
    
    interface IThirdPartyLoginPresenter<V : IThirdPartyLoginView, M : IThirdPartyLoginModel> :
        IPresenter<V, M> {
        fun otherLogin(mark: String?, uid: String?, token: String?)
    }

    interface IThirdPartyLoginModel : IModel {
        fun otherLogin(mark: String?, uid: String?, token: String?)
    }
}
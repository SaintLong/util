package com.saint.util.util.toast;


import com.saint.util.util.AppUtil;

/**
 * Created by 朱志强 on 2018/9/8.
 */
final class ToastDelegate implements AbstractToastVariety.ShowCallback {
    private Setting mSetting;
    private AbstractToastVariety mCurToastVariety;

    private ToastDelegate() {

    }

    private static ToastDelegate sToastDelegate;


    static ToastDelegate get() {
        if (sToastDelegate == null) {
            sToastDelegate = new ToastDelegate();
        }

        return sToastDelegate;
    }

    public static boolean hasCreated() {
        return sToastDelegate != null;
    }

    public boolean isDismissOnLeave() {
        return (hasSetting() && setting().isDismissOnLeave()) || !AppUtil.isNotificationPermitted();
    }

    public <ApiToInvoke> ApiToInvoke getToastVarietyByTag(int tag) {
        return (ApiToInvoke) ToastCache.get().retrieveToastTagFromCache(tag);
    }


    public DefaultToastVariety getDefaultToastVariety() {
        return (DefaultToastVariety) ToastCache.get().retrieveToastTagFromCache(hasSetting() ? setting().getDefaultToastTag() : ToastTags.TOAST_TAG_SRC);
    }


    public EmotionToastVariety getEmotionToastVariety() {
        return (EmotionToastVariety) ToastCache.get().retrieveToastTagFromCache(AbstractToastVariety.TOAST_TAG_EMOTION);
    }

    public DarkToastVariety getDarkToastVariety() {
        return (DarkToastVariety) ToastCache.get().retrieveToastTagFromCache(AbstractToastVariety.TOAST_TAG_DARK);
    }

    public boolean isShowing() {
        return mCurToastVariety != null && mCurToastVariety.isShowing();
    }

    public void dismiss() {
        if (mCurToastVariety != null && mCurToastVariety.isShowing()) {
            mCurToastVariety.dismiss();
        }
    }

    public Setting setting() {
        if (mSetting == null) {
            mSetting = new Setting();
        }

        return mSetting;
    }

    public boolean hasSetting() {
        return mSetting != null;
    }

    @Override
    public void beforeShow(AbstractToastVariety toastVariety) {
        if (mCurToastVariety != null && toastVariety.mTag != mCurToastVariety.mTag) {
            dismiss();
        }
    }

    @Override
    public void afterShow(AbstractToastVariety toastVariety) {
        mCurToastVariety = toastVariety;
    }
}

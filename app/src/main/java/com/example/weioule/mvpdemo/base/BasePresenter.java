package com.example.weioule.mvpdemo.base;

import android.content.Context;


/**
 * Author by weioule.
 * Date on 2018/10/29.
 */
public abstract class BasePresenter<V, M extends BaseModel> {

    private V mView;
    private M mModel;
    protected Context mContext;

    public void attachView(V view) {


        mView = view;
        mModel = createModel();

    }

    public abstract M createModel();

    public V getMvpView() {
        checkViewAttached();
        return mView;
    }

    public M getMvpModel() {
        checkViewAttached();
        checkModelExist();
        return mModel;
    }

    public void detachView() {
        this.mView = null;
        this.mModel = null;
    }

    public void checkViewAttached() {
        if (null == mView) {
            throw new MvpViewNotAttachedException();
        }
    }

    public void checkModelExist() {
        if (null == mModel) {
            throw new ModelNotCreateException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter没绑定View");
        }
    }

    public static class ModelNotCreateException extends RuntimeException {
        public ModelNotCreateException() {
            super("Please create the Model before you request data 没创建Model");
        }
    }

}

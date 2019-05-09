package com.pacia.ptms.utils.http;

import android.content.Context;

import com.pacia.ptms.utils.DialogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * rxjava Schedulers封装类
 * Created by p on 2018/05/31.
 */

public class SchedulersHelper {
    private static DialogUtils dialogUtils;

    public static <T> ObservableTransformer<T, T> io_main(final Context context,
                                                          final boolean isShow,final String msg) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                if (isShow && dialogUtils == null) {
                    dialogUtils = new DialogUtils();
                }
                dialogUtils.createLoadingDialog(context, msg, true);
                return upstream
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
//                                if (isShow) {
//                                    if (!dialogUtils.isShowDialog()) {
//                                        disposable.dispose();
//                                    }
//                                }
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (null != dialogUtils) {
                                    dialogUtils.dismissLoading();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}

package com.whamu2.wanandroid.mvp.ui.dialog;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.aspect.CheckLogin;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.utils.ArmsUtils;
import com.whamu2.wanandroid.R;
import com.whamu2.wanandroid.common.event.EventObj;
import com.whamu2.wanandroid.databinding.FragmentMoreBottomBinding;
import com.whamu2.wanandroid.mvp.model.bean.Articles;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.utils.SingletonLoadHelper;
import com.whamu2.wanandroid.utils.Transformer;
import com.whamu2.wanandroid.utils.observer.Callback;
import com.whamu2.wanandroid.utils.observer.CommonSubscriber;

import org.simple.eventbus.EventBus;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.whamu2.wanandroid.common.Container.Const.ORIGIN_COLLECT_LIST;
import static com.whamu2.wanandroid.common.Container.Event.COLLECT_STATE_CHANGE;

/**
 * @author suming
 * @date 19:28 2019 May Mon
 * @des https://github.com/whamu2
 */
public class MoreBottomDialog extends BottomSheetDialogFragment {
    private static final String KEY_DATA = "DATA";
    private static final String KEY_ORIGIN = "origin";

    private FragmentMoreBottomBinding mBinding;

    private RxErrorHandler mErrorHandler;

    private Articles mArticles;
    private int origin;

    public static MoreBottomDialog newInstance(Articles articles, int origin) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_DATA, articles);
        args.putInt(KEY_ORIGIN, origin);
        MoreBottomDialog fragment = new MoreBottomDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticles = getArguments().getParcelable(KEY_DATA);
            origin = getArguments().getInt(KEY_ORIGIN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_bottom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mErrorHandler = ArmsUtils.obtainAppComponentFromContext(Objects.requireNonNull(getContext())).rxErrorHandler();
        mBinding = DataBindingUtil.bind(view);
        if (mBinding != null) {
            mBinding.setListener(this::onClick);

            if (TextUtils.isEmpty(mArticles.getProjectLink())) {
                mBinding.actionLink.setVisibility(View.GONE);
            }

            mBinding.actionFavorites.setText(mArticles.isCollect() ? "已收藏" : "收藏");
            mBinding.actionFavorites.setCompoundDrawablesRelativeWithIntrinsicBounds(0,
                    mArticles.isCollect() ? R.drawable.ic_favorite_selected_24dp : R.drawable.ic_favorite_black_24dp,
                    0, 0);
        }
    }


    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.action_browser) {
            Uri uri = Uri.parse(Objects.requireNonNull(mArticles).getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (id == R.id.action_link) {
            if (mArticles != null && !TextUtils.isEmpty(mArticles.getProjectLink())) {
                Uri uri = Uri.parse(mArticles.getProjectLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        } else if (id == R.id.action_cancel) {
            dismiss();

        } else if (id == R.id.action_favorites) {
            onCheckLogin(R.id.action_favorites);
        }

    }


    @CheckLogin
    public void onCheckLogin(int id) {
        if (id == R.id.action_favorites) {
            final boolean isCollect = mArticles.isCollect();

            Observable.just(isCollect)
                    .doOnComplete(() -> {

                    })
                    .flatMap((Function<Boolean, ObservableSource<BaseResp<Object>>>) a -> {
                        Observable<BaseResp<Object>> cancelObservable = origin == ORIGIN_COLLECT_LIST ?
                                SingletonLoadHelper.register(getContext()).cancelMyCollect(mArticles.getId()) :
                                SingletonLoadHelper.register(getContext()).cancelSquareCollect(mArticles.getId());

                        return a ? cancelObservable :
                                SingletonLoadHelper.register(getContext()).doInternalCollect(mArticles.getId());
                    })
                    .compose(Transformer.cutoverSchedulers())
                    .subscribe(new CommonSubscriber<>(mErrorHandler, new Callback<BaseResp<Object>>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseResp<Object> resp) {
                            if (resp.isSuccess()) {
                                mBinding.actionFavorites.setText(isCollect ? "收藏" : "已收藏");
                                mBinding.actionFavorites.setCompoundDrawablesRelativeWithIntrinsicBounds(0,
                                        isCollect ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_selected_24dp,
                                        0, 0);
                                mArticles.setCollect(!isCollect);
                                EventBus.getDefault().post(new EventObj(COLLECT_STATE_CHANGE, !isCollect));
                            } else {
                                ToastUtils.showShort(resp.getErrorMsg());
                            }
                        }
                    }));

        }
    }
}

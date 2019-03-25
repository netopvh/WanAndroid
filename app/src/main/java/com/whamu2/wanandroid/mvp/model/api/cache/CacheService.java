
package com.whamu2.wanandroid.mvp.model.api.cache;

import com.whamu2.wanandroid.mvp.model.bean.BannerBean;
import com.whamu2.wanandroid.mvp.model.bean.BaseResp;
import com.whamu2.wanandroid.mvp.model.bean.Cycle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * 缓存
 *
 * @author whamu2
 * @date 2018/6/26
 */
public interface CacheService {

    /**
     * banner缓存
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<BaseResp<List<BannerBean>>>> getCacheBanner(Observable<BaseResp<List<BannerBean>>> observable,
                                                                 DynamicKey dynamicKey,
                                                                 EvictProvider evictProvider);

    /**
     * 知识体系缓存
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<BaseResp<List<Cycle>>>> getCacheTree(Observable<BaseResp<List<Cycle>>> observable,
                                                          DynamicKey dynamicKey,
                                                          EvictProvider evictProvider);
}

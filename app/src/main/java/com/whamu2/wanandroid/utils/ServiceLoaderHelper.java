package com.whamu2.wanandroid.utils;

import com.whamu2.wanandroid.utils.exception.ServiceNotFoundException;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Eric
 * @date 2019/4/4
 * @github https://github.com/whamu2
 */
public class ServiceLoaderHelper {

    public static <T> T load(Class<T> tClass) throws ServiceNotFoundException {
        final ServiceLoader<T> loader = ServiceLoader.load(tClass);
        final Iterator<T> iterator = loader.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            throw new ServiceNotFoundException();
        }
    }
}

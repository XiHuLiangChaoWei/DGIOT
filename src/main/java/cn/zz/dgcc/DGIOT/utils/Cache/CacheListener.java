package cn.zz.dgcc.DGIOT.utils.Cache;

import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/5/21 14:14
 * ClassExplain :
 * ->
 */
public class CacheListener {
        Logger logger = Logger.getLogger("cacheLog");
        private final CacheManagerImpl cacheManagerImpl;
        public CacheListener(CacheManagerImpl cacheManagerImpl) {
            this.cacheManagerImpl = cacheManagerImpl;
        }

        public void startListen() {
            new Thread(){
                public void run() {
                    while (true) {
                        for(String key : cacheManagerImpl.getAllKeys()) {
                            if (cacheManagerImpl.isTimeOut(key)) {
                                cacheManagerImpl.clearByKey(key);
                                logger.info(key + "缓存被清除");
                            }
                        }
                    }
                }
            }.start();

        }

}

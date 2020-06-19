package com.merrycodes.handler.global.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MerryCodes
 * @date 2020/6/11 9:16
 */
public class GlobalExceptionContext {

    private static GlobalExceptionContext instance;

    private static final Map<String, GlobalExceptionStrategy> GLOBAL_EXCEPTION_STRATEGY_MAP = new HashMap<>(3);

    static {
        GLOBAL_EXCEPTION_STRATEGY_MAP.put("tags", new TagsStrategy());
        GLOBAL_EXCEPTION_STRATEGY_MAP.put("category", new CategoryStrategy());
        GLOBAL_EXCEPTION_STRATEGY_MAP.put("article", new ArticleStrategy());
    }

    public GlobalExceptionStrategy getStrategy(String key) {
        return GLOBAL_EXCEPTION_STRATEGY_MAP.get(key);
    }

    /**
     * 反射
     */
    private GlobalExceptionContext() {
        if (instance != null) {
            throw new RuntimeException();
        }
    }

    public static GlobalExceptionContext getInstance() {
        if (instance == null) {
            instance = new GlobalExceptionContext();
        }
        return instance;
    }

}

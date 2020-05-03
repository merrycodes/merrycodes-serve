package com.merrycodes.utils;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 自定义toStringStyle
 *
 * @author MerryCodes
 * @date 2020/5/3 10:16
 */
public class ToStringStyleUtils {

    public static final ToStringStyle NO_NULL_STYLE = new NoNullToStringStyle();

    private static class NoNullToStringStyle extends ToStringStyle {
        public NoNullToStringStyle() {
            super();
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
        }

        @Override
        public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
            if (value != null) {
                super.append(buffer, fieldName, value, fullDetail);
            }
        }
    }
}

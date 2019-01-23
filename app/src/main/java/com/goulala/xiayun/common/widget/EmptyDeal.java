package com.goulala.xiayun.common.widget;

import android.widget.TextView;


import com.goulala.xiayun.common.utils.ToastUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by：Z_B on 2018/6/15 11:11
 * Effect： 对于空数据的判断处理
 */
public final class EmptyDeal {
    public EmptyDeal() {
    }

    public static String dealNull(String dest) {
        if (dest == null) {
            dest = "";
            return "";
        } else {
            return dest;
        }
    }

    public static <T> List<T> dealNull(List<T> list) {
        return list == null ? (List<T>) Collections.emptyList() : list;
    }

    public static Map<?, ?> dealNull(Map<?, ?> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    public static Set<?> dealNull(Set<?> set) {
        return set == null ? Collections.emptySet() : set;
    }

    public static Object[] dealNull(Object[] array) {
        return array == null ? new Object[0] : array;
    }

    public static Object dealNull(Object obj) {
        return obj == null ? new Object() : obj;
    }

    public static boolean isLessThan(List<?> dest, int size) {
        return dest == null || dest.size() < size;
    }

    public static int size(List<?> dest) {
        return dest == null ? 0 : dest.size();
    }

    public static int size(TextView dest) {
        return dest == null ? 0 : dest.getText().length();
    }

    public static boolean isEmpty(List<?> dest) {
        return dest == null || dest.size() == 0;
    }

    public static boolean isEmpty(String dest) {
        return dest == null || dest.length() == 0 || "".equals(dest);
    }

    public static boolean isEmpty(TextView dest) {
        return dest == null || dest.getText().toString().trim().equals("");
    }

    public static boolean isEmpty(Map<?, ?> dest) {
        return dest == null || 0 == dest.size();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Object dest) {
        if (dest instanceof List) {
            return isEmpty((List) dest);
        } else if (dest instanceof Map) {
            return isEmpty((Map) dest);
        } else if (dest instanceof String) {
            return isEmpty((String) dest);
        } else if (dest instanceof TextView) {
            return isEmpty((TextView) dest);
        } else {
            return dest == null;
        }
    }

    public static boolean Empty(Object dest) {
        return isEmpty(dest);
    }

    public static boolean Empty(Object dest, String hint) {
        if (isEmpty(dest)) {
            ToastUtils.showToast(hint);
            return true;
        } else {
            return false;
        }
    }
}

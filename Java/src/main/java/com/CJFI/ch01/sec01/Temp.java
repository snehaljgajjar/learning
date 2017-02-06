package com.CJFI.ch01.sec01;

import com.google.common.base.Throwables;

import java.lang.reflect.Method;

/**
 * @author: pgajjar
 * @since: 7/29/16
 */
public class Temp {
    public static void main(String[] args) {
        float f = 1f / 0f;
        System.out.println("f: " + f);
        String s = String.valueOf(f);
        System.out.println("s: " + s);

        Test t = (Test) invokeMethod(Test.class, null, "getTest", 10, 15);
//        System.out.println("\nTest: " + t);
        System.out.println("\nTest.x: " + invokeMethod(Test.class, t, "getX"));
        System.out.println("\nTest.y: " + invokeMethod(Test.class, t, "getY"));
    }

    public static Object invokeMethod(Class<?> clazz, Object object, String methodName, Object... params) {
        try {
            Class<?>[] paramClasses = null;
            if (params != null) {
                paramClasses = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramClasses[i] = params[i].getClass();
                }
            }
            Method method = clazz.getDeclaredMethod(methodName, paramClasses);
            return method.invoke(object, params);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}

package dev.cryptic.aspect.api.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassUtil {
    public static boolean isMethodOverridden(Class<?> implementingClass, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = implementingClass.getDeclaredMethod(methodName, parameterTypes);

            return method.getDeclaringClass().equals(implementingClass);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    public static Method[] getDeclaredMethodsOfType(Class<?> clazz, Class<?> returnType) {
        return Arrays.stream(getDeclaredMethods(clazz)).filter(method -> method.getReturnType().equals(returnType)).toArray(Method[]::new);
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    public static Field[] getDeclaredFieldsOfType(Class<?> clazz, Class<?> type) {
        return Arrays.stream(getDeclaredFields(clazz)).filter(field -> field.getType().equals(type)).toArray(Field[]::new);
    }

    public static Class<?>[] getMethodParameterTypes(Method method) {
        return method.getParameterTypes();
    }



}

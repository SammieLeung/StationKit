package com.station.kit.view.mvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author: Sam Leung
 * date:  2021/10/28
 */
public class ViewDataBindingHelper {

    /**
     * 生成ViewDataBinding
     *
     * @param context
     * @param <VDB>
     * @return
     */
    public static <VDB extends ViewDataBinding> VDB inflateVDB(Context context, Class clazz) {
        Class modelClass = getViewDataBindingModelClass(clazz);
        if (modelClass != null) {
            Method inflate = null;
            try {
                inflate = modelClass.getDeclaredMethod("inflate", LayoutInflater.class);
                return (VDB) inflate.invoke(null, LayoutInflater.from(context));

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成ViewDataBinding
     *
     * @param context
     * @param <VDB>
     * @return
     */
    public static <VDB extends ViewDataBinding> VDB inflateVDB(Context context, ViewGroup parent, Class clazz) {
        Class modelClass = getViewDataBindingModelClass(clazz);
        if (modelClass != null) {
            Method inflate = null;
            try {
                inflate = modelClass.getDeclaredMethod("inflate", LayoutInflater.class,ViewGroup.class,boolean.class);
                return (VDB) inflate.invoke(null, LayoutInflater.from(context),parent,false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取ViewDataBinding的具体类
     *
     * @param clazz
     * @return
     */
    private static Class getViewDataBindingModelClass(Class clazz) {
        Class modelClass = null;
        Type type = clazz.getGenericSuperclass();
        if (type == null)
            return null;
        if (type instanceof ParameterizedType) {
            ParameterizedType tmpType = (ParameterizedType) type;
            for (Type t : tmpType.getActualTypeArguments()) {
                if (t instanceof Class && instanceOfViewDataBinding((Class) t))
                    modelClass = (Class) t;
            }
            if (modelClass == null)
                modelClass = getViewDataBindingModelClass(clazz.getSuperclass());
        } else {
            modelClass = getViewDataBindingModelClass(clazz.getSuperclass());
        }
        return modelClass;
    }

    /**
     * 判断一个类是否继承ViewDataBinding
     *
     * @param clazz
     * @return
     */
    private static boolean instanceOfViewDataBinding(Class clazz) {
        if (clazz == null)
            return false;
        if (!ViewDataBinding.class.isAssignableFrom(clazz))
            return instanceOfViewDataBinding(clazz.getSuperclass());
        else
            return true;
    }
}

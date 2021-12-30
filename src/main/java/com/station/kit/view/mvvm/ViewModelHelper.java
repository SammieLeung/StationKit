package com.station.kit.view.mvvm;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author: Sam Leung
 * date:  2021/10/28
 */
public class ViewModelHelper {

    /**
     * 生成ViewModel
     * @param context
     * @param owner
     * @param <VM>
     * @return
     */
    public static <VM extends ViewModel> VM createAndroidViewModel(Context context, ViewModelStoreOwner owner,Class clazz) {
        Class modelClass = getViewModelClass(clazz);
        return (VM) new ViewModelProvider(owner).get(modelClass);

    }

    /**
     * 获取ViewModel具体类
     * @param clazz
     * @return
     */
    private static Class getViewModelClass(Class clazz) {
        Class modelClass = null;
        Type type = clazz.getGenericSuperclass();
        if (type == null)
            return null;
        if (type instanceof ParameterizedType) {
            ParameterizedType tmpType = (ParameterizedType) type;
            for (Type t : tmpType.getActualTypeArguments()) {
                if (instanceOfViewModel((Class) t))
                    modelClass = (Class) t;
            }
            if (modelClass == null)
                modelClass = getViewModelClass(clazz.getSuperclass());
        } else {
            modelClass = getViewModelClass(clazz.getSuperclass());
        }
        return modelClass;
    }

    /**
     * 判断是否继承于ViewMdoel
     * @param clazz
     * @return
     */
    private static boolean instanceOfViewModel(Class clazz) {
        if (clazz == null)
            return false;
        if (!ViewModel.class.isAssignableFrom(clazz))
            return instanceOfViewModel(clazz.getSuperclass());
        else
            return true;
    }
}

package com.xiongyingqi.jackson.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 在当前线程内保存ObjectMapper供Jackson2HttpMessageConverter使用
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/1 0001.
 */
public class ThreadJacksonMixInHolder {
    private static ThreadLocal<ThreadJacksonMixInHolder> holderThreadLocal = new ThreadLocal<ThreadJacksonMixInHolder>();
    private Set<Map.Entry<Class<?>, Class<?>>> mixIns;
    private ObjectMapper mapper;
    private org.codehaus.jackson.map.ObjectMapper codehausMapper;

    /**
     * 根据当前MixIn集合生成objectMapper<p>
     * <p>
     * <b>注意：该方法在返回mapper对象之后调用clear方法，如果再次调用builderMapper()肯定会保存</b>
     *
     * @return
     */
    public static ObjectMapper builderMapper() {
        ThreadJacksonMixInHolder holder = holderThreadLocal.get();
        if (holder.mapper == null && isContainsMixIn()) {
            holder.mapper = new ObjectMapper();
            for (Map.Entry<Class<?>, Class<?>> mixIn : holder.mixIns) {
                holder.mapper.addMixInAnnotations(mixIn.getKey(), mixIn.getValue());
            }
        }
        clear();// 如果不调用clear可能导致线程内的数据是脏的！
        return holder.mapper;
    }

    /**
     * 根据当前MixIn集合生成objectMapper
     *
     * @return
     */
    public static org.codehaus.jackson.map.ObjectMapper builderCodehausMapper() {
        ThreadJacksonMixInHolder holder = holderThreadLocal.get();
        if (holder.codehausMapper == null && isContainsMixIn()) {
            holder.codehausMapper = new org.codehaus.jackson.map.ObjectMapper();
            for (Map.Entry<Class<?>, Class<?>> mixIn : holder.mixIns) {
                holder.codehausMapper.getDeserializationConfig().addMixInAnnotations(mixIn.getKey(), mixIn.getValue());
                holder.codehausMapper.getSerializationConfig().addMixInAnnotations(mixIn.getKey(), mixIn.getValue());
            }
        }
        clear();// 如果不调用clear可能导致线程内的数据是脏的！
        return holder.codehausMapper;
    }

    /**
     * 清除当前线程内的数据
     */
    public static void clear() {
        holderThreadLocal.set(null);
//        holderThreadLocal.remove();
    }

    /**
     * 设置MixIn集合到线程内，如果线程内已经存在数据，则会先清除
     *
     * @param resetMixIns
     */
    public static void setMixIns(Set<Map.Entry<Class<?>, Class<?>>> resetMixIns) {
        ThreadJacksonMixInHolder holder = holderThreadLocal.get();
        if (holder == null) {
            holder = new ThreadJacksonMixInHolder();
            holderThreadLocal.set(holder);
        }
        holder.mixIns = resetMixIns;
    }

    /**
     * 不同于setMixIns，addMixIns为增加MixIn集合到线程内，即不会清除已经保存的数据
     * <br>2014年4月4日 下午12:08:15
     *
     * @param toAddMixIns
     */
    public static void addMixIns(Set<Map.Entry<Class<?>, Class<?>>> toAddMixIns) {
        ThreadJacksonMixInHolder holder = holderThreadLocal.get();
        if (holder == null) {
            holder = new ThreadJacksonMixInHolder();
            holderThreadLocal.set(holder);
        }
        if (holder.mixIns == null) {
            holder.mixIns = new HashSet<Map.Entry<Class<?>, Class<?>>>();
        }
        holder.mixIns.addAll(toAddMixIns);
    }

    /**
     * 获取线程内的MixIn集合<p></p>
     * <b>注意：为了防止线程执行完毕之后仍然存在有数据，请务必适时调用clear()方法</b>
     *
     * @return
     * @see com.xiongyingqi.jackson.helper.ThreadJacksonMixInHolder#builderMapper()
     * @see com.xiongyingqi.jackson.helper.ThreadJacksonMixInHolder#builderCodehausMapper()
     * @see com.xiongyingqi.jackson.helper.ThreadJacksonMixInHolder#clear()
     */
    public static Set<Map.Entry<Class<?>, Class<?>>> getMixIns() {
        ThreadJacksonMixInHolder holder = holderThreadLocal.get();
        return holder.mixIns;
    }

    /**
     * 判断当前线程是否存在MixIn集合
     *
     * @return
     */
    public static boolean isContainsMixIn() {
        if (holderThreadLocal.get() == null) {
            return false;
        }
        if (holderThreadLocal.get().mixIns != null && holderThreadLocal.get().mixIns.size() > 0) {
            return true;
        }
        return false;
    }

}

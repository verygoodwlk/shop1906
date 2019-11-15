package com.qf.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelUtil {

    /**
     * uid - Channel
     */
    private static Map<Integer, Channel> map = new ConcurrentHashMap<>();

    /**
     * 添加映射关系
     * @param uid
     * @param channel
     */
    public static void add(Integer uid, Channel channel){
        map.put(uid, channel);
    }

    /**
     * 根据key移除映射关系
     * @param uid
     */
    public static void removeChannel(Integer uid){
        map.remove(uid);
    }

    /**
     * 根据channel对象移除映射关系
     * @param channel
     */
    public static void removeChannel(Channel channel){
        for (Map.Entry<Integer, Channel> entry : map.entrySet()) {
            if(entry.getValue() == channel){
                map.remove(entry.getKey());
                return;
            }
        }
    }

    /**
     * 根据uid获得channel对象
     * @param uid
     * @return
     */
    public static Channel getUid(Integer uid){
        return map.get(uid);
    }
}

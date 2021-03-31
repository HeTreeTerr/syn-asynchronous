package com.hss.service;

/**
 * 【拯救世界】接口
 */
public interface SaveTheWorldService {

    /**
     * 同步【拯救世界】
     * @param keyNo
     * @return
     */
    String synSaveTheWorld(String keyNo);

    /**
     * 异步【拯救世界】
     * @param keyNo
     * @return
     */
    String asySaveTheWorld(String keyNo);

    /**
     * 处理结果通知
     * @param keyNo
     * @return
     */
    Boolean resultNotice(String keyNo,Integer status);
}

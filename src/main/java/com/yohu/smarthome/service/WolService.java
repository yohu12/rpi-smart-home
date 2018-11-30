package com.yohu.smarthome.service;

import com.yohu.smarthome.bean.WOLNode;
import com.yohu.smarthome.exception.UnableToWakeUpWOLNodeException;
import org.springframework.stereotype.Service;

/**
 * @author: huyong
 * @date: 2018/11/30 16:47
 */
@Service
public class WolService {

    public void wakeUP(String mackAddress) throws UnableToWakeUpWOLNodeException {
        WOLNode node = new WOLNode(mackAddress);
        node.wakeUP();
    }
}

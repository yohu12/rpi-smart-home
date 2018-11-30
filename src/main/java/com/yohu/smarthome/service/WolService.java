package com.yohu.smarthome.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yohu.smarthome.bean.WOLNode;
import com.yohu.smarthome.dao.MacAddressRepository;
import com.yohu.smarthome.entity.MacAddress;
import com.yohu.smarthome.entity.QMacAddress;
import com.yohu.smarthome.exception.UnableToWakeUpWOLNodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * @author: huyong
 * @date: 2018/11/30 16:47
 */
@Service
public class WolService {


    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
        System.out.println("init JPAQueryFactory successfully");
    }

    @Autowired
    private MacAddressRepository macAddressRepository;

    public void wakeUP(String mackAddress) throws UnableToWakeUpWOLNodeException {
        WOLNode node = new WOLNode(mackAddress);
        node.wakeUP();
    }

    public MacAddress save(MacAddress macAddress) {
        return macAddressRepository.save(macAddress);
    }

    public void deleteById(Long macAddressId) {
        macAddressRepository.deleteById(macAddressId);
    }

    public long deleteByMacAddress(String macAddress) {
        QMacAddress qMacAddress = QMacAddress.macAddress;
        Predicate predicate = qMacAddress.address.eq(macAddress);

        return queryFactory.delete(qMacAddress).where(predicate).execute();
    }
}

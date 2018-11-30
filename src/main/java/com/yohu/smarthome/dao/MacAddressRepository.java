package com.yohu.smarthome.dao;

import com.yohu.smarthome.entity.MacAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author: huyong
 * @date: 2018/11/30 18:01
 */
public interface MacAddressRepository extends JpaRepository<MacAddress, Long>, QuerydslPredicateExecutor<MacAddress> {
}

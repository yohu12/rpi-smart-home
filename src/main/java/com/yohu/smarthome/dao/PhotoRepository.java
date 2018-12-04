package com.yohu.smarthome.dao;

import com.yohu.smarthome.entity.MacAddress;
import com.yohu.smarthome.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author: huyong
 * @date: 2018/11/30 18:01
 */
public interface PhotoRepository extends JpaRepository<Photo, Long>, QuerydslPredicateExecutor<Photo> {
}

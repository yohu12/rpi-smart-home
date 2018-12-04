package com.yohu.smarthome.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yohu.smarthome.bean.WOLNode;
import com.yohu.smarthome.dao.MacAddressRepository;
import com.yohu.smarthome.dao.PhotoRepository;
import com.yohu.smarthome.entity.MacAddress;
import com.yohu.smarthome.entity.Photo;
import com.yohu.smarthome.entity.QMacAddress;
import com.yohu.smarthome.entity.QPhoto;
import com.yohu.smarthome.exception.UnableToWakeUpWOLNodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author: huyong
 * @date: 2018/11/30 16:47
 */
@Service
public class PhotoService {


    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Autowired
    private PhotoRepository photoRepository;

    public List<Photo> save(List<Photo> photoList) {
        return photoRepository.saveAll(photoList);
    }

    public void delete(List<Photo> photoList) {
        photoRepository.deleteAll(photoList);
    }

    @Transactional
    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    @Transactional
    public long deleteByUrl(List<String> urlList) {
        QPhoto qPhoto = QPhoto.photo;
        Predicate predicate = qPhoto.photoUrl.in(urlList);

        return queryFactory.delete(qPhoto).where(predicate).execute();
    }
}

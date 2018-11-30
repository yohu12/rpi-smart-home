package com.yohu.smarthome.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


/**
 * @author huyong
 */
public class EntityListener {

  /**
   * 保存前处理
   * 
   * @param entity 基类
   */
  @PrePersist
  public void prePersist(BaseEntity entity) {
    entity.setCreateDate(new Date());
    entity.setModifyDate(new Date());
    entity.setIsdel(false);
  }

  /**
   * 更新前处理
   * 
   * @param entity 基类
   */
  @PreUpdate
  public void preUpdate(BaseEntity entity) {
    entity.setModifyDate(new Date());
  }

}

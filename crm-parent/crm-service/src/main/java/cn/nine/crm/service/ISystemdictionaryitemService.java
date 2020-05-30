package cn.nine.crm.service;

import cn.nine.crm.domain.Systemdictionaryitem;
import cn.nine.crm.query.SystemdictionaryitemQuery;

import java.util.List;

/**
 * (Systemdictionaryitem)表Service层接口
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
public interface ISystemdictionaryitemService extends IBaseService<Systemdictionaryitem,Long,SystemdictionaryitemQuery>{

    List<Systemdictionaryitem> selectByName(String name);

}
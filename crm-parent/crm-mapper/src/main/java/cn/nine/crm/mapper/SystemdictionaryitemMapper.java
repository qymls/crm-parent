package cn.nine.crm.mapper;

import cn.nine.crm.domain.Systemdictionaryitem;
import cn.nine.crm.query.SystemdictionaryitemQuery;

import java.util.List;

/**
 * (Systemdictionaryitem)表数据库访问层
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
public interface SystemdictionaryitemMapper extends BaseMapper<Systemdictionaryitem,Long,SystemdictionaryitemQuery>{

    List<Systemdictionaryitem> selectByName(String name);

}
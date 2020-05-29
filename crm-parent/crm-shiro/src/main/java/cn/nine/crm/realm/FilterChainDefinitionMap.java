package cn.nine.crm.realm;


import cn.nine.crm.domain.Permission;
import cn.nine.crm.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterChainDefinitionMap {
    @Autowired
    private PermissionMapper permissionMapper;

    public Map<String, String> create() {
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("/Admin/gitHubLogin", "anon");
        map.put("/Admin/qqLogin", "anon");
        map.put("/employee/login", "anon");
        map.put("/swagger-ui.html","anon");
        map.put("/webjars/**","anon");
        map.put("/v2/**","anon");
        map.put("/swagger-resources/**","anon");
        map.put("*.js","anon");
        map.put("/configuration/ui","anon");
        map.put("*.html","anon");

       /* map.put("/Admin/Employee/save", "perms[employee:save]");
        map.put("/Admin/Employee/delete", " perms[employee:delete]");
        map.put("/Admin/Employee/findAll", "perms[employee:findALL]");*/
        //拿到所有权限
        List<Permission> permissions = permissionMapper.findAllPermission();
        //2.遍历权限，拿到权限与资源
        for (Permission permission : permissions) {
            String url = permission.getUrl();//资源
            String sn = permission.getSn();//权限
            //把路径与资源放到拦截中去
            map.put(url, "customPerms[" + sn + "]");/*customPerms来自自定义权限过滤器的key。xml文件*/
        }
        map.put("/**", "authc");
        return map;
    }
}

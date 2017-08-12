package com.ivan.dubbo.dao;

import java.util.List;
import java.util.Map;

import org.ivan.entity.admin.SysUcenterFunction;


/**
 * @author buyuer
 * @version
 */
public interface SysUcenterFunctionMapper extends BaseMapper<SysUcenterFunction> {

    /**
     * 
     * @author buyuer
     * @Title: selectFunList
     * @Description: 查询所有权限列表
     */
    List<Map<String, Object>> selectFunList();

    /**
     * 
     * @author buyuer
     * @Title: updateFunction
     * @Description: 修改状态
     * @param function
     */
    void updateFunction(SysUcenterFunction function);

    /**
     * 
     * @author buyuer
     * @Title: selectFunByUser
     * @Description: 根据用户id查询菜单列表
     * @param userId 用户id
     * @param appId 应用id
     */
    List<Map<String, Object>> selectFunByUser(Map<String, Object> map);
}
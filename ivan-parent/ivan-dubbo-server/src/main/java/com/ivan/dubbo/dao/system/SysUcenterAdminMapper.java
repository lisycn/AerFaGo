package com.ivan.dubbo.dao.system;

import java.util.List;
import java.util.Map;

import org.ivan.entity.admin.SysUcenterAdmin;

import com.ivan.dubbo.dao.BaseMapper;
public interface SysUcenterAdminMapper extends BaseMapper<SysUcenterAdmin>{
	public void insertByEntity(SysUcenterAdmin sysUcenterAdmin);
	public void deleteByEntity(Map<String,Object> map);
	public void updateByEntity(Map<String,Object> map);
	public void updateByEntity(SysUcenterAdmin sysUcenterAdmin);
	public List<SysUcenterAdmin> selectByObject (Map<String,Object> map);
	public SysUcenterAdmin selectSingle(Map<String,Object> map);
	public SysUcenterAdmin selectSingle(SysUcenterAdmin sysUcenterAdmin);
}

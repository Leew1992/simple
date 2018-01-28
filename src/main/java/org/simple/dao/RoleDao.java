package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.dto.RoleDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
import org.simple.entity.RoleDO;
import org.simple.entity.RoleMenuDO;
import org.simple.entity.RoleSystemDO;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {

    /**
     * 获取角色信息
     */
    RoleDO getRoleById(String idRole);
    
    /**
     * 获取角色信息
     */
    RoleDO getRoleByName(String roleName);
    
    /**
     * 获取角色列表
     */
    List<RoleDO> listRolesByIdGroup(Map<String, Object> paramMap);
    
    /**
     * 获取角色列表
     */
    List<RoleDTO> listRolesByIdUser(String idUser);
    
    /**
     * 获取角色系统列表
     */
    List<RoleSystemDO> listRoleSystemsByIdRole(String idRole);
    
    /**
     * 获取角色列表
     */
    List<RoleDO> pagingRoles(Map<String, Object> paramMap);
    
    /**
     * 获取角色树
     */
    List<TreeNode> getRoleTreeForUserList(String idUser);
    
    /**
     * 获取角色系统信息
     */
    RoleSystemDO getHasPermitedRoleSystem(Map<String, Object> paramMap);
    
    /**
     * 获取有权限的菜单列表
     */
    List<MenuDO> listHasPermitedMenus(Map<String, Object> paramMap);
    
    /**
     * 保存角色信息
     */
    void saveRole(RoleDO roleDO);
    
    /**
     * 更新角色信息
     */
    void updateRole(RoleDO roleDO);
    
    /**
     * 删除角色信息
     */
    void deleteRole(String idRole);
    
    /**
     * 批量删除角色信息
     */
    void batchDeleteRoles(List<String> idRoles);
    
    /**
     * 保存角色菜单
     */
    void saveRoleMenu(RoleMenuDO roleMenuDO);
    
    /**
     * 保存角色系统信息
     */
	void saveRoleSystem(RoleSystemDO roleSystemDO);
    
    /**
     * 删除角色菜单
     */
    void deleteRoleMenusByIdRole(String idRole);
    
    /**
     * 删除角色菜单
     */
    void deleteRoleMenusByIdMenu(String idMenu);
    
    /**
     * 删除被取消的角色菜单
     */
    void deleteCanceledRoleMenu(Map<String, Object> paramMap);
    
    /**
     * 删除角色系统
     */
    void deleteRoleSystemsByIdRole(String idRole);
    
    /**
     * 批量删除角色菜单
     */
    void batchDeleteRoleMenus(List<String> idRoles);

}
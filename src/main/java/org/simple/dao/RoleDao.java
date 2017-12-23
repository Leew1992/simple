package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.dto.RoleDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.RoleDO;
import org.simple.entity.RoleMenuDO;
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
     * 获取角色列表
     */
    List<RoleDO> pagingRoles(RoleDTO roleDTO);
    
    /**
     * 获取角色树
     */
    List<TreeNode> getRoleTreeForUserList(String idUser);
    
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
     * 删除角色菜单
     */
    void deleteRoleMenu(String idRole);
    
    /**
     * 批量删除角色菜单
     */
    void batchDeleteRoleMenus(List<String> idRoles);

}
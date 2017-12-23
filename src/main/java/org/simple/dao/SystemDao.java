package org.simple.dao;

import java.util.List;

import org.simple.dto.TreeNode;
import org.simple.entity.SystemDO;
import org.simple.entity.SystemMenuDO;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemDao {
    
	/**
	 * 获取系统信息
	 */
    SystemDO getSystemById(String idSystem);
    
    /**
     * 获取系统列表
     */
    List<SystemDO> getSystemByIdParent(String idParent);
    
    /**
     * 获取系统树
     */
    List<TreeNode> getSystemTreeForMenuList();

    /**
     * 获取系统树
     */
    List<TreeNode> getSystemTreeForMenuForm(String idMenu);
    
    /**
     * 获取系统树
     */
    List<TreeNode> getSystemTreeForSystemConfig();
    
    /**
     * 获取系统列表
     */
    List<SystemDO> listAllSystems();

    /**
     * 保存系统信息
     */
    void saveSystem(SystemDO systemDO);

    /**
     * 更新系统信息
     */
    void updateSystem(SystemDO systemDO);

    /**
     * 删除系统信息
     */
    void deleteSystem(String idSystem);
    
    /**
     * 批量批除系统信息
     */
    void batchDeleteSystems(List<String> idSystems);
    
    /**
     * 获取系统菜单
     */
    SystemMenuDO getSystemMenuByIdMenu(String idMenu);
    
    /**
     * 保存系统菜单信息
     */
    void saveSystemMenu(SystemMenuDO systemMenuDO);

    /**
     * 删除系统菜单信息
     */
    void deleteSystemMenu(String idMenu);
    
    /**
     * 批量删除系统菜单信息
     */
    void batchDeleteSystemMenus(List<String> idMenus);

}
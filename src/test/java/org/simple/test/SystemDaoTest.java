package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.SystemDao;
import org.simple.dto.TreeNode;
import org.simple.entity.SystemDO;
import org.simple.entity.SystemMenuDO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class SystemDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private SystemDao systemDao;

	/**
	 * 获取系统信息
	 */
    @Test
    public void testGetSystemById() {
    	SystemDO systemDO = systemDao.getSystemById("0");
    	logger.debug(JSON.toJSONString(systemDO));
    }
    
    /**
	 * 获取系统信息
	 */
    @Test
    public void testGetSystemByIdParent() {
    	String idParent = "0";
    	List<SystemDO> systemList = systemDao.getSystemByIdParent(idParent);
    	logger.debug(JSON.toJSONString(systemList));
    }
    
    /**
	 * 获取系统树
	 */
    @Test
    public void testGetSystemTreeForMenuForm() {
    	List<TreeNode> treeList = systemDao.getHasCheckedSystemTreeByIdMenu("0");
    	logger.debug(JSON.toJSONString(treeList));
    }
    
    /**
	 * 获取系统列表
	 */
    @Test
    public void testListAllSystems() {
    	List<SystemDO> systemList = systemDao.listAllSystems();
    	logger.debug(JSON.toJSONString(systemList));
    }
    
    /**
     * 保存系统信息
     */
    @Test
    public void testSaveSystem() {
    	SystemDO systemDO = new SystemDO();
    	systemDO.setIdParent("0");
    	systemDO.setSystemName("aa");
    	systemDO.setSystemDesc("aa");
    	systemDO.setCreatedBy("aa");
    	systemDO.setCreatedDate(new Date());
    	systemDO.setUpdatedBy("aa");
    	systemDO.setUpdatedDate(new Date());
    	systemDao.saveSystem(systemDO);
    	logger.debug("系统信息删除成功");
    }
    
    /**
     * 更新系统信息
     */
    @Test
    public void testUpdateSystem() {
    	SystemDO systemDO = new SystemDO();
    	systemDO.setIdSystem("1");
    	systemDO.setSystemName("bb");
    	systemDO.setSystemDesc("bb");
    	systemDO.setCreatedBy("bb");
    	systemDO.setCreatedDate(new Date());
    	systemDO.setUpdatedBy("bb");
    	systemDO.setUpdatedDate(new Date());
    	systemDao.updateSystem(systemDO);
    	logger.debug("系统信息更新成功");
    }
    
    /**
     * 删除系统信息
     */
    @Test
    public void testDeleteSystem() {
    	String idSystem = "1";
    	systemDao.deleteSystem(idSystem);
    	logger.debug("系统信息删除成功");
    }
    
    /**
     * 批量删除系统信息
     */
    @Test
    public void testbatchDeleteSystems() {
    	List<String> idSystems = new ArrayList<>();
    	idSystems.add("0");
    	idSystems.add("1");
    	systemDao.batchDeleteSystems(idSystems);
    	logger.debug("系统信息批量删除成功");
    }
    
    /**
     * 获取系统菜单信息
     */
    @Test
    public void testGetSystemMenuByIdMenu() {
    	String idMenu = "0";
    	SystemMenuDO systemMenu = systemDao.getSystemMenuByIdMenu(idMenu);
    	logger.debug(JSON.toJSONString(systemMenu));
    }
    
    /**
     * 保存系统菜单信息
     */
    @Test
    public void testSaveSystemMenu() {
    	SystemMenuDO systemMenuDO = new SystemMenuDO();
    	systemMenuDO.setIdSystem("1");
    	systemMenuDO.setIdMenu("1");
    	systemMenuDO.setCreatedBy("aa");
    	systemMenuDO.setCreatedDate(new Date());
    	systemMenuDO.setUpdatedBy("aa");
    	systemMenuDO.setUpdatedDate(new Date());
    	systemDao.saveSystemMenu(systemMenuDO);
    	logger.debug("系统菜单信息保存成功");
    }
    
    /**
     * 删除系统菜单信息
     */
    @Test
    public void testDeleteSystemMenu() {
    	String idSystem = "1";
    	systemDao.deleteSystem(idSystem);
    	logger.debug("系统菜单信息删除成功");
    }
    
    /**
     * 批量删除系统菜单信息
     */
    @Test
    public void testBatchDeleteSystemMenus() {
    	List<String> idMenus = new ArrayList<>();
    	idMenus.add("0");
    	idMenus.add("1");
    	systemDao.batchDeleteSystemMenus(idMenus);
    	logger.debug("系统菜单批量删除成功");
    }
}

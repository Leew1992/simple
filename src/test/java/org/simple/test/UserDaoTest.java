package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.UserDao;
import org.simple.dto.QueryDTO;
import org.simple.dto.UserDTO;
import org.simple.entity.UserDO;
import org.simple.entity.UserRoleDO;
import org.simple.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class UserDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private UserDao userDao;

	/**
	 * 获取用户信息
	 */
    @Test
    public void testQueryUser() throws Exception {
    	UserDO userDO = userDao.getUserById("40284e815e80a91e015e80ac16840000");
    	logger.debug(JSON.toJSONString(userDO));
    }
    
    /**
	 * 获取用户信息
	 */
    @Test
    public void testPageUsers() {
    	UserDTO userDTO = new UserDTO();
    	QueryDTO queryDTO = new QueryDTO();
    	Map<String, Object> paramMap = BeanUtil.convertBeansToMap(userDTO, queryDTO);
    	List<UserDO> userList = userDao.pagingUsers(paramMap);
    	logger.debug(JSON.toJSONString(userList));
    }
    
    /**
	 * 保存用户信息
	 */
    @Test
    public void testSaveUser() {
    	UserDO userDO = new UserDO();
    	userDO.setUserName("aa");
    	userDO.setPassword("aa");
    	userDO.setIsEnabled(true);
    	userDO.setCreatedBy("aa");
    	userDO.setUpdatedBy("bb");
    	userDao.saveUser(userDO);
    	logger.debug("用户信息保存成功");
    }
    
    /**
	 * 更新用户信息
	 */
    @Test
    public void testUpdateUser() {
    	UserDO userDO = new UserDO();
    	userDO.setIdUser("0");
    	userDO.setUserName("bb");
    	userDO.setPassword("bb");
    	userDO.setIsEnabled(true);
    	userDO.setCreatedBy("bb");
    	userDO.setCreatedDate(new Date());
    	userDO.setUpdatedBy("bb");
    	userDO.setUpdatedDate(new Date());
    	userDao.updateUser(userDO);
    	logger.debug("用户信息更新成功");
    }
    
    /**
	 * 删除用户信息
	 */
    @Test
    public void testDeleteUser() {
    	String idUser = "0";
    	userDao.deleteUser(idUser);
    	logger.debug("用户信息删除成功");
    }
    
    /**
	 * 批量删除用户信息
	 */
    @Test
    public void testbatchDeleteUsers() {
    	List<String> idUsers = new ArrayList<>();
    	idUsers.add("0");
    	idUsers.add("1");
    	userDao.batchDeleteUsers(idUsers);
    	logger.debug("用户信息批量删除成功");
    }
    
    /**
	 * 保存用户角色信息
	 */
    @Test
    public void testSaveUserRole() {
    	UserRoleDO userRoleDO = new UserRoleDO();
    	userRoleDO.setIdUser("1");
    	userRoleDO.setIdRole("1");
    	userDao.saveUserRole(userRoleDO);
    	logger.debug("用户信息保存成功");
    }
    
    /**
	 * 删除用户角色信息
	 */
    @Test
    public void testDeleteUserRole() {
    	String idUserRole = "1";
    	userDao.deleteUserRoleByIdUser(idUserRole);
    	logger.debug("用户角色信息删除成功");
    }
    
    /**
	 * 批量删除用户角色信息
	 */
    @Test
    public void testBatchDeleteUserRoles() {
    	List<String> idUserRoles = new ArrayList<>();
    	idUserRoles.add("1");
    	idUserRoles.add("2");
    	userDao.batchDeleteUserRolesByIdUsers(idUserRoles);
    	logger.debug("用户角色信息批量删除成功");
    }
    
}

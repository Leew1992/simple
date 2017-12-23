package org.simple.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.constant.SysCodeConsts;
import org.simple.dao.GroupDao;
import org.simple.dao.SmsDao;
import org.simple.dao.UserDao;
import org.simple.dto.PageDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.UserDTO;
import org.simple.entity.GroupUserDO;
import org.simple.entity.UserDO;
import org.simple.entity.UserRoleDO;
import org.simple.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口实现
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private GroupDao groupDao;
	
	@Resource
	private SmsDao smsDao;
	
	@Override
	public UserDO getUserById(String idUser) {
		return userDao.getUserById(idUser);
	}
	
	@Override
	public UserDO getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}
	
	@Override
	public PageDTO pagingUsers(UserDTO userDTO) {
		List<UserDO> userList = userDao.pagingUsers(userDTO);
		PageDTO page = new PageDTO();
		page.setRows(userList);
		page.setTotal(userList.size());
		return page;
	}

	@Override
	public ResultDTO register(UserDTO userDTO) {
		// 用户信息
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setUserName(userDTO.getMobilephone());
		userDO.setCreatedBy("system");
		userDO.setCreatedDate(new Date());
		userDO.setUpdatedBy("system");
		userDO.setUpdatedDate(new Date());
		userDO.setStatus(SysCodeConsts.ENABLED);
		userDO.setSalt("123");
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		userDO.setPassword(shaPasswordEncoder.encodePassword(userDO.getPassword(), "123"));
		userDao.saveUser(userDO);
		
		// 字典-用户
		GroupUserDO groupUserDO = new GroupUserDO();
		groupUserDO.setIdGroup("0");
		groupUserDO.setIdUser(userDO.getIdUser());
		groupUserDO.setCreatedBy("system");
		groupUserDO.setCreatedDate(new Date());
		groupUserDO.setUpdatedBy("system");
		groupUserDO.setUpdatedDate(new Date());
		groupDao.saveGroupUser(groupUserDO);
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveUser(UserDTO userDTO) {
		// 用户信息
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setCreatedBy("system");
		userDO.setCreatedDate(new Date());
		userDO.setUpdatedBy("system");
		userDO.setUpdatedDate(new Date());
		userDO.setStatus(SysCodeConsts.ENABLED);
		userDO.setSalt("123");
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		userDO.setPassword(shaPasswordEncoder.encodePassword(userDO.getPassword(), "123"));
		userDao.saveUser(userDO);
		
		// 字典-用户
		String[] idGroupArr = userDTO.getIdGroups().split(",");
		for(String idGroup : idGroupArr) {
			GroupUserDO groupUserDO = new GroupUserDO();
			groupUserDO.setIdGroup(idGroup);
			groupUserDO.setIdUser(userDO.getIdUser());
			groupUserDO.setCreatedBy("system");
			groupUserDO.setCreatedDate(new Date());
			groupUserDO.setUpdatedBy("system");
			groupUserDO.setUpdatedDate(new Date());
			groupDao.saveGroupUser(groupUserDO);
		}
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO assignRolesForUserList(String idUser, String idRoles) {
		// 删除用户角色信息
		userDao.deleteUserRole(idUser);
		
		String[] idRoleArr = idRoles.split(",");
		for(String idRole : idRoleArr) {
			UserRoleDO userRoleDO = new UserRoleDO();
			userRoleDO.setIdUser(idUser);
			userRoleDO.setIdRole(idRole);
			userRoleDO.setCreatedBy("system");
			userRoleDO.setCreatedDate(new Date());
			userRoleDO.setUpdatedBy("system");
			userRoleDO.setUpdatedDate(new Date());
			userDao.saveUserRole(userRoleDO);
		}
		return new ResultDTO(true, MessageConsts.ASSIGN_SUCCESS);
	}
	
	@Override
	public ResultDTO updateUser(UserDTO userDTO) {
		// 用户信息
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setCreatedBy("system");
		userDO.setCreatedDate(new Date());
		userDO.setUpdatedBy("system");
		userDO.setUpdatedDate(new Date());
		userDO.setStatus(SysCodeConsts.ENABLED);
		userDao.updateUser(userDO);
		groupDao.deleteGroupUserByIdUser(userDO.getIdUser());
		
		// 字典-用户
		String[] idGroupArr = userDTO.getIdGroups().split(",");
		for(String idGroup : idGroupArr) {
			GroupUserDO groupUserDO = new GroupUserDO();
			groupUserDO.setIdGroup(idGroup);
			groupUserDO.setIdUser(userDO.getIdUser());
			groupUserDO.setCreatedBy("system");
			groupUserDO.setCreatedDate(new Date());
			groupUserDO.setUpdatedBy("system");
			groupUserDO.setUpdatedDate(new Date());
			groupDao.saveGroupUser(groupUserDO);
		}
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteUsers(String idUsers) {
		String[] idUserArr = idUsers.split(",");
		userDao.batchDeleteUsers(Arrays.asList(idUserArr));
		groupDao.batchDeleteGroupsUserByIdUser(Arrays.asList(idUserArr));
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}
}

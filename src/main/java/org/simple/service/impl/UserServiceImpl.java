package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.constant.SysCodeConsts;
import org.simple.context.UserContext;
import org.simple.dao.GroupDao;
import org.simple.dao.MailDao;
import org.simple.dao.SmsDao;
import org.simple.dao.UserDao;
import org.simple.dto.PageDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.UserDTO;
import org.simple.entity.GroupUserDO;
import org.simple.entity.MailSendRecordDO;
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
	
	@Resource
	private MailDao mailDao;
	
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
	public Integer countUsers(List<String> accessDates) {
		return userDao.countUsers(accessDates);
	}

	@Override
	public ResultDTO register(UserDTO userDTO) {
		MailSendRecordDO mailSendRecordDO = mailDao.getLastestMailSendRecordByFrom(userDTO.getEmail());
		String extractWord = mailSendRecordDO.getExtractWord();
		// 验证码校验不成功，不给予注册
		if(userDTO != null && !userDTO.getVerifyCode().equals(extractWord)) {
			return new ResultDTO(true, MessageConsts.SAVE_FAILURE);
		}
		
		// 添加用户
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setCreatedBy(UserContext.getCurrentUserName());
		userDO.setUpdatedBy(UserContext.getCurrentUserName());
		userDO.setIsEnabled(true);
		userDO.setSalt(SysCodeConsts.ENCRYPTION_SALT);
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		userDO.setPassword(shaPasswordEncoder.encodePassword(userDO.getPassword(), userDO.getSalt()));
		userDao.saveUser(userDO);
		
		// 添加分组用户
		GroupUserDO groupUserDO = new GroupUserDO();
		groupUserDO.setIdGroup(SysCodeConsts.DEFAULT_GROUP_ID);
		groupUserDO.setIdUser(userDO.getIdUser());
		groupUserDO.setCreatedBy(UserContext.getCurrentUserName());
		groupUserDO.setUpdatedBy(UserContext.getCurrentUserName());
		groupDao.saveGroupUser(groupUserDO);
		
		// 添加用户角色
		UserRoleDO userRoleDO = new UserRoleDO();
		userRoleDO.setIdUser(userDO.getIdUser());
		userRoleDO.setIdRole(SysCodeConsts.DEFAULT_ROLE_ID);
		userRoleDO.setCreatedBy(UserContext.getCurrentUserName());
		userRoleDO.setUpdatedBy(UserContext.getCurrentUserName());
		userDao.saveUserRole(userRoleDO);
		
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveUser(UserDTO userDTO) {
		// 用户信息
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setCreatedBy(UserContext.getCurrentUserName());
		userDO.setUpdatedBy(UserContext.getCurrentUserName());
		userDO.setIsEnabled(true);
		userDO.setSalt(SysCodeConsts.ENCRYPTION_SALT);
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		userDO.setPassword(shaPasswordEncoder.encodePassword(userDO.getPassword(), "123"));
		userDao.saveUser(userDO);
		
		// 字典-用户
		String[] idGroupArr = userDTO.getIdGroups().split(",");
		for(String idGroup : idGroupArr) {
			GroupUserDO groupUserDO = new GroupUserDO();
			groupUserDO.setIdGroup(idGroup);
			groupUserDO.setIdUser(userDO.getIdUser());
			groupUserDO.setCreatedBy(UserContext.getCurrentUserName());
			groupUserDO.setUpdatedBy(UserContext.getCurrentUserName());
			groupDao.saveGroupUser(groupUserDO);
		}
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO assignRolesForUserList(String idUser, String idRoles) {
		// 删除用户角色信息
		userDao.deleteUserRoleByIdUser(idUser);
		
		String[] idRoleArr = idRoles.split(",");
		for(String idRole : idRoleArr) {
			UserRoleDO userRoleDO = new UserRoleDO();
			userRoleDO.setIdUser(idUser);
			userRoleDO.setIdRole(idRole);
			userRoleDO.setCreatedBy(UserContext.getCurrentUserName());
			userRoleDO.setUpdatedBy(UserContext.getCurrentUserName());
			userDao.saveUserRole(userRoleDO);
		}
		return new ResultDTO(true, MessageConsts.ASSIGN_SUCCESS);
	}
	
	@Override
	public ResultDTO updateUser(UserDTO userDTO) {
		// 用户信息
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setCreatedBy(UserContext.getCurrentUserName());
		userDO.setUpdatedBy(UserContext.getCurrentUserName());
		userDO.setIsEnabled(true);
		userDao.updateUser(userDO);
		groupDao.deleteGroupUserByIdUser(userDO.getIdUser());
		
		// 字典-用户
		String[] idGroupArr = userDTO.getIdGroups().split(",");
		for(String idGroup : idGroupArr) {
			GroupUserDO groupUserDO = new GroupUserDO();
			groupUserDO.setIdGroup(idGroup);
			groupUserDO.setIdUser(userDO.getIdUser());
			groupUserDO.setCreatedBy(UserContext.getCurrentUserName());
			groupUserDO.setUpdatedBy(UserContext.getCurrentUserName());
			groupDao.saveGroupUser(groupUserDO);
		}
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteUsers(String idUsers) {
		String[] idUserArr = idUsers.split(",");
		userDao.batchDeleteUsers(Arrays.asList(idUserArr));
		groupDao.batchDeleteGroupsUserByIdUser(Arrays.asList(idUserArr));
		userDao.batchDeleteUserRolesByIdUsers(Arrays.asList(idUserArr));
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

}

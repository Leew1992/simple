package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.simple.constant.MessageConst;
import org.simple.constant.SysCodeConst;
import org.simple.context.UserContext;
import org.simple.dao.GroupDao;
import org.simple.dao.MailDao;
import org.simple.dao.SmsDao;
import org.simple.dao.UserDao;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.UserDTO;
import org.simple.entity.GroupUserDO;
import org.simple.entity.MailSendRecordDO;
import org.simple.entity.UserDO;
import org.simple.entity.UserRoleDO;
import org.simple.service.UserService;
import org.simple.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口实现
 */
@Service
public class UserServiceImpl implements UserService {
	
	private final Logger logger = Logger.getLogger(getClass());
	
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
	public PageDTO pagingUsers(UserDTO userDTO, QueryDTO queryDTO) {
		Map<String, Object> paramMap = BeanUtil.convertBeansToMap(userDTO, queryDTO);
		List<UserDO> userList = userDao.pagingUsers(paramMap);
		PageDTO page = new PageDTO();
		page.setRows(userList);
		page.setTotal(userList.size());
		return page;
	}
	
	@Override
	public Integer countUsersBasedFuzzy(String createdDate) {
		return userDao.countUsersBasedFuzzy(createdDate);
	}
	
	@Override
	public Integer countUsersBasedAccurate(List<String> createdDates) {
		return userDao.countUsersBasedAccurate(createdDates);
	}

	@Override
	public ResultDTO register(UserDTO userDTO) {
		MailSendRecordDO mailSendRecordDO = mailDao.getNewestMailSendRecord(userDTO.getEmail());
		if(mailSendRecordDO == null) {
			logger.info("未找到验证码发送记录...");
			return new ResultDTO(false, MessageConst.LOAD_FAILURE);
		}
		
		String extractWord = mailSendRecordDO.getExtractWord();
		if(Objects.isNull(extractWord)) {
			logger.info("未找到验证码...");
			return new ResultDTO(false, MessageConst.LOAD_FAILURE);
		}
		
		String inputVerifyCode = userDTO.getVerifyCode();
		if(Objects.isNull(inputVerifyCode)) {
			logger.info("用户未输入验证码...");
			return new ResultDTO(false, MessageConst.LOAD_FAILURE);
		}
		
		if(!Objects.equals(extractWord, inputVerifyCode)) {
			logger.info("用户输入验证码不正确...");
			return new ResultDTO(false, MessageConst.LOAD_FAILURE);
		}
		
		// 添加用户
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setCreatedBy(UserContext.getCurrentUserName());
		userDO.setUpdatedBy(UserContext.getCurrentUserName());
		userDO.setIsEnabled(true);
		userDO.setSalt(SysCodeConst.ENCRYPTION_SALT);
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		userDO.setPassword(shaPasswordEncoder.encodePassword(userDO.getPassword(), userDO.getSalt()));
		userDao.saveUser(userDO);
		
		// 添加分组用户
		GroupUserDO groupUserDO = new GroupUserDO();
		groupUserDO.setIdGroup(SysCodeConst.DEFAULT_GROUP_ID);
		groupUserDO.setIdUser(userDO.getIdUser());
		groupUserDO.setCreatedBy(UserContext.getCurrentUserName());
		groupUserDO.setUpdatedBy(UserContext.getCurrentUserName());
		groupDao.saveGroupUser(groupUserDO);
		
		// 添加用户角色
		UserRoleDO userRoleDO = new UserRoleDO();
		userRoleDO.setIdUser(userDO.getIdUser());
		userRoleDO.setIdRole(SysCodeConst.DEFAULT_ROLE_ID);
		userRoleDO.setCreatedBy(UserContext.getCurrentUserName());
		userRoleDO.setUpdatedBy(UserContext.getCurrentUserName());
		userDao.saveUserRole(userRoleDO);
		
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveUser(UserDTO userDTO) {
		// 用户信息
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userDTO, userDO);
		userDO.setCreatedBy(UserContext.getCurrentUserName());
		userDO.setUpdatedBy(UserContext.getCurrentUserName());
		userDO.setIsEnabled(true);
		userDO.setSalt(SysCodeConst.ENCRYPTION_SALT);
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
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
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
		return new ResultDTO(true, MessageConst.ASSIGN_SUCCESS);
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
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteUsers(String idUsers) {
		String[] idUserArr = idUsers.split(",");
		userDao.batchDeleteUsers(Arrays.asList(idUserArr));
		groupDao.batchDeleteGroupsUserByIdUser(Arrays.asList(idUserArr));
		userDao.batchDeleteUserRolesByIdUsers(Arrays.asList(idUserArr));
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

}

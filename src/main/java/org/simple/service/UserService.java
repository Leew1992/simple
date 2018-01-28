package org.simple.service;

import java.util.List;

import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.UserDTO;
import org.simple.entity.UserDO;

/**
 * 用户服务接口
 *
 */
public interface UserService {
	
	/**
	 * 获取用户信息
	 */
	UserDO getUserById(String idUser);
	
	/**
	 * 获取用户信息
	 */
	UserDO getUserByName(String userName);
	
	/**
	 * 获取用户分页列表
	 */
	PageDTO pagingUsers(UserDTO userDTO, QueryDTO queryDTO);
	
	/**
	 * 统计用户数量
	 */
	Integer countUsersBasedFuzzy(String createdDate);
	
	/**
	 * 统计用户数量
	 */
	Integer countUsersBasedAccurate(List<String> createdDates);
	
	/**
	 * 保存用户信息
	 */
	ResultDTO saveUser(UserDTO userDTO);
	
	/**
	 * 更新用户信息
	 */
	ResultDTO updateUser(UserDTO userDTO);
	
	/**
	 * 删除用户信息
	 */
	ResultDTO batchDeleteUsers(String idUsers) ;
	
	/**
	 * 分配用户角色信息
	 */
	ResultDTO assignRolesForUserList(String idUser, String idRoles);

	/**
	 * 注册用户信息
	 */
	ResultDTO register(UserDTO userDTO);
	
}

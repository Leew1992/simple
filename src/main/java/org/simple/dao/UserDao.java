package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.entity.UserDO;
import org.simple.entity.UserRoleDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

	/**
	 * 获取用户信息
	 */
	UserDO getUserById(String idUser);
	
	/**
	 * 获取用户信息
	 */
	UserDO getUserByName(String userName);
	
	/**
	 * 获取用户分页信息
	 */
	List<UserDO> pagingUsers(Map<String, Object> paramMap);
	
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
	void saveUser(UserDO userDO);
	
	/**
	 * 更新用户信息
	 */
	void updateUser(UserDO userDO);
	
	/**
	 * 删除用户信息
	 */
	void deleteUser(String idUser);
	
	/**
	 * 批量删除用户信息
	 */
	void batchDeleteUsers(List<String> idUsers);
	
	/**
	 * 保存用户角色信息
	 */
	void saveUserRole(UserRoleDO userRoleDO);
	
	/**
	 * 删除用户角色信息
	 */
	void deleteUserRoleByIdUser(String idUser);
	
	/**
	 * 批量删除用户角色信息
	 */
	void batchDeleteUserRolesByIdUsers(List<String> idUsers);
}

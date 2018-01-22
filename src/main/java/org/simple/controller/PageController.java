package org.simple.controller;

import org.simple.annotation.MonitorAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制器
 */
@Controller
public class PageController {
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "/login.do")
    public String login() {
        return "redirect:/login.html";
    }
	
	/**
	 * 首页
	 */
	@RequestMapping(value = "/index.do")
    public String index() {
        return "redirect:/index.html";
    }
	
	/**
	 * 看板
	 */
	@MonitorAccess
	@RequestMapping(value = "/index/dashboard.do")
    public String dashboard() {
        return "dashboard";
    }
	
	/**
	 * 用户列表
	 */
	@MonitorAccess
	@RequestMapping(value = "/user/userList.do")
    public String home() {
        return "user-list";
    }
	
	/**
	 * 用户表单
	 */
	@MonitorAccess
	@RequestMapping(value = "/user/userForm.do")
    public String userForm() {
        return "user-form";
    }
	
	/**
	 * 角色列表
	 */
	@MonitorAccess
	@RequestMapping(value = "/role/roleList.do")
    public String roleList() {
        return "role-list";
    }
	
	/**
	 * 角色表单
	 */
	@MonitorAccess
	@RequestMapping(value = "/role/roleForm.do")
    public String roleForm() {
        return "role-form";
    }
	
	/**
	 * 菜单列表
	 */
	@MonitorAccess
	@RequestMapping(value = "/menu/menuList.do")
    public String menuList() {
        return "menu-list";
    }
	
	/**
	 * 菜单表单
	 */
	@MonitorAccess
	@RequestMapping(value = "/menu/menuForm.do")
    public String menuForm() {
        return "menu-form";
    }
	
	/**
	 * 贴子列表
	 */
	@MonitorAccess
	@RequestMapping(value = "/post/postList.do")
    public String postList() {
        return "post-list";
    }
	
	/**
	 * 贴子表单
	 */
	@MonitorAccess
	@RequestMapping(value = "/post/postForm.do")
    public String postForm() {
        return "post-form";
    }
	
	/**
	 * 贴子列表
	 */
	@MonitorAccess
	@RequestMapping(value = "/comment/commentList.do")
    public String commentList() {
        return "comment-list";
    }
	
	/**
	 * 分组配置
	 */
	@MonitorAccess
	@RequestMapping(value = "/config/groupConfig.do")
    public String groupConfig() {
        return "group-config";
    }
	
	/**
	 * 系统配置
	 */
	@MonitorAccess
	@RequestMapping(value = "/config/systemConfig.do")
    public String systemConfig() {
        return "system-config";
    }
	
	/**
	 * 栏目配置
	 */
	@MonitorAccess
	@RequestMapping(value = "/config/columnConfig.do")
    public String columnConfig() {
        return "column-config";
    }
	
	/**
	 * 类别配置
	 */
	@MonitorAccess
	@RequestMapping(value = "/config/categoryConfig.do")
    public String categoryConfig() {
        return "category-config";
    }
	
	/**
	 * 登录访问
	 */
	@MonitorAccess
	@RequestMapping(value = "/stat/loginAccess.do")
    public String statAccess() {
        return "login-access";
    }
	
	/**
	 * 栏目访问
	 */
	@MonitorAccess
	@RequestMapping(value = "/stat/columnAccess.do")
    public String columnAccess() {
        return "column-access";
    }
	
	/**
	 * 附件上传
	 */
	@MonitorAccess
	@RequestMapping(value = "/stat/attachmentUpload.do")
    public String attachUpload() {
        return "attachment-upload";
    }
	
	/**
	 * 附件列表
	 */
	@MonitorAccess
	@RequestMapping(value = "/attachment/attachmentList.do")
    public String attachmentList() {
        return "attachment-list";
    }
	
	/**
	 * 关系运算
	 */
	@MonitorAccess
	@RequestMapping(value = "/relation/relationCalculate.do")
    public String relationCalculate() {
        return "relation-calculate";
    }
}
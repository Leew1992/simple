package org.simple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制器
 */
@Controller
public class PageController {
	
	/**
	 * 框架
	 */
	@RequestMapping(value = "/index.do")
    public String admin() {
        return "index";
    }
	
	/**
	 * 框架
	 */
	@RequestMapping(value = "/home/index_page.do")
    public String index() {
        return "redirect:/index.html";
    }
	
	/**
	 * 首页
	 */
	@RequestMapping(value = "/home/home_page.do")
    public String home() {
        return "home/home";
    }
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "/home/signIn_page.do")
    public String login() {
        return "redirect:/login.html";
    }
	
	/**
	 * 找回密码
	 */
	@RequestMapping(value = "/home/forgotPassword_page.do")
    public String forgotPassword() {
        return "home/forgot_password";
    }
	
	/**
	 * 用户列表
	 */
	@RequestMapping(value = "/user/list_page.do")
    public String user() {
        return "user/user";
    }
	
	/**
	 * 角色列表
	 */
	@RequestMapping(value = "/role/list_page.do")
    public String role() {
        return "role/role";
    }
	
	/**
	 * 用户新增
	 */
	@RequestMapping(value="/user/add_page.do")
	public String addUser() {
		return "user/user-form";
	}
	
	/**
	 * 权限管理
	 */
	@RequestMapping(value = "/menu/list_page.do")
    public String menu() {
        return "menu/menu";
    }
	
	/**
	 * 贴子新增
	 */
	@RequestMapping(value = "/post/add_page.do")
	public String addPost() {
		return "post/post-form";
	}
	
	/**
	 * 贴子管理
	 */
	@RequestMapping(value = "/post/list_page.do")
	public String post() {
		return "post/post";
	}
	
	/**
	 * 评论管理
	 */
	@RequestMapping(value = "/comment/list_page.do")
	public String comment() {
		return "comment/comment";
	}
	
	/**
	 * 字典管理
	 */
	@RequestMapping(value = "/manage/list_page.do")
	public String dict() {
		return "config/config";
	}
}
package com.pzy.controller.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.entity.Category;
import com.pzy.entity.Project;
import com.pzy.entity.User;
import com.pzy.service.CategoryService;
import com.pzy.service.KnowledgeService;
import com.pzy.service.PrescriptionService;
import com.pzy.service.ProjectService;
import com.pzy.service.UserService;
/***
 * 前台，首页各种连接登陆等
 * @author qq:263608237
 *
 */
@Controller
@RequestMapping("/")
public class FrontController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private PrescriptionService prescriptionService;
	@Autowired
	private KnowledgeService knowledgeService;
	@Autowired
	private com.pzy.service.JinjiService jinjiService;
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request,   ServletRequestDataBinder binder) throws Exception {   
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true)); 
	}  
	/***
	 * 跳转到首页
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		return "index";
	}
	

	/***
	 * 关于我们
	 * @return
	 */
	@RequestMapping("about")
	public String about() {
		return "about";
	}
	/***
	 * 注册连接
	 * @return
	 */
	@RequestMapping("center")
	public String center() {
		return "center";
	}
	/***
	 * 我的订单
	 * @return
	 */
	@RequestMapping("myorder")
	public String myorder(Model model,HttpSession httpSession) {
		User user=(User)httpSession.getAttribute("user");
		return "myorder";
	}
	/***
	 * 取消订单
	 * @param id
	 * @param model
	 * @param httpSession
	 * @return
	 */
	
	
	
	/***
	 * 注册连接
	 * @return
	 */
	@RequestMapping("register")
	public String register() {
		return "register";
	}
	/***
	 * 点击注册
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("doregister")
	public String register(User user,Model model) {
		user.setCreateDate(new Date());
		model.addAttribute("tip","注册成功请登录！");
		userService.save(user);
		return "login";
	}
	/***
	 * 登陆连接
	 * @return
	 */
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	/***
	 * 退出操作清空缓存等操作
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping("loginout")
	public String loginout(HttpSession httpSession,Model model) {
		httpSession.removeAttribute("user");
		return "index";
	}
	
	
	
	/***
	 * 执行登陆动作
	 * @param user
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping("dologin")
	public String dologin(User user,HttpSession httpSession,Model model) {
		User loginuser=userService.login(user.getUsername(), user.getPassword());
    	if(loginuser!=null){
    		httpSession.setAttribute("user", loginuser);
            return "index"; 
    	}
    	else{
    		httpSession.removeAttribute("user");
    		model.addAttribute("tip","登陆失败 不存在此用户名或密码!");
    		return "login";
    	}
	}
	
	/***
	 *家务帮列表
	 * @param model
	 * @return
	 */
	@RequestMapping("project")
	public String project(Model model,String key,Long cid) {
		model.addAttribute("cagegorys", categoryService.findAll());
		Category category=null;
		if(cid!=null)
			category=categoryService.find(cid);
		List<Project> list= projectService.findBycategory(cid);
		model.addAttribute("projects",list);
		model.addAttribute("category",category);
		return "project";
	}
	
	@RequestMapping("prescription")
	public String prescription(Model model,String key,Long cid) {
		model.addAttribute("lists",prescriptionService.findAll());
		return "prescription";
	}
	@RequestMapping("knowledge")
	public String knowledge(Model model,String key,Long cid) {
		model.addAttribute("lists",knowledgeService.findAll());
		return "knowledge";
	}
	
	@RequestMapping("jinji")
	public String jinji(Model model,String key,Long cid) {
		model.addAttribute("lists",jinjiService.findAll());
		return "jinji";
	}
	
	/**
	 * 查看产品列表
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("viewproject")
	public String viewproject(Long id,Model model) {
		model.addAttribute("bean",projectService.find(id));
		return "viewproject";
	}
	@RequestMapping("viewknowledge")
	public String viewknowledge(Long id,Model model) {
		model.addAttribute("bean",this.knowledgeService.find(id));
		return "viewknowledge";
	}
	@RequestMapping("viewprescription")
	public String viewprescription(Long id,Model model) {
		model.addAttribute("bean",this.prescriptionService.find(id));
		return "viewprescription";
	}
	@RequestMapping("viewjinji")
	public String viewjinji(Long id,Model model) {
		model.addAttribute("bean",this.jinjiService.find(id));
		return "viewjinji";
	}
	
}


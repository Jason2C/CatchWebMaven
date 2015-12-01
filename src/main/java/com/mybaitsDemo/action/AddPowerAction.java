package com.mybaitsDemo.action;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatisDemo.entity.Content;
import com.mybatisDemo.mapper.ContentMapper;
import com.task.fanli99Task;
import com.util.Page;

@Controller
@RequestMapping("**/admin")
public class AddPowerAction {
	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private fanli99Task fanli99Task;

	@SuppressWarnings("all")
	@RequestMapping({ "/testjson" })
	public String json(HttpServletRequest request, String username,
			String power, Model model, Invocation invocation)
			throws IOException {

		// 当前环境 MappedStatement，BoundSql，及sql取得
		/*
		 * MappedStatement
		 * mappedStatement=(MappedStatement)invocation.getArgs()[0]; Object
		 * parameter = invocation.getArgs()[1]; BoundSql boundSql =
		 * mappedStatement.getBoundSql(parameter); String originalSql =
		 * boundSql.getSql().trim(); Object parameterObject =
		 * boundSql.getParameterObject();
		 */

		try {
			String resource = "configuration.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory session = new SqlSessionFactoryBuilder()
					.build(reader);
			SqlSession getSession = session.openSession();

			ContentMapper contentMapper = getSession
					.getMapper(ContentMapper.class);
			int pageNum = 0;
			int pageSize = 0;
			try {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			// 先设置了startPage，然后再select
			PageHelper.startPage(pageNum, pageSize);
			List<Content> contentList = contentMapper.selectContentPage();

			PageInfo<Content> page = new PageInfo(contentList);
			request.setAttribute("page", page);

			// fanli99Task.execute();
			// getSession.commit();
			// HttpSession session1 = request.getSession();
			// session1.setAttribute("name", "Tom");
			// session1.setMaxInactiveInterval(6565);
			// ServletContext ContextA
			// =request.getSession().getServletContext();
			// ContextA.setAttribute("session", request.getSession());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("err", "查询出错:" + e.getMessage());
		}

		return "/index";
	}

	@RequestMapping(value = "/user/users")
	public String list(
			@RequestParam(required = false, defaultValue = "1") int pageNo,
			@RequestParam(required = false, defaultValue = "5") int pageSize,
			@ModelAttribute("name") String name,
			@ModelAttribute("levelId") String levelId,
			@ModelAttribute("subjectId") String subjectId, Model model) {
		// 这里是“信使”诞生之地，一出生就加载了很多重要信息！
		Page page = Page.newBuilder(pageNo, pageSize, "users");
		page.getParams().put("name", name); // 这里再保存查询条件
		page.getParams().put("levelId", levelId);
		page.getParams().put("subjectId", subjectId);

		model.addAttribute("users", contentMapper.selectByNameLevelSubject(
				name, levelId, subjectId, page));
		model.addAttribute("page", page); // 这里将page返回前台
		return USER_LIST_JSP;
	}
}

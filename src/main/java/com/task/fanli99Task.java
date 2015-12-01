package com.task;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.mybatisDemo.entity.Content;
import com.mybatisDemo.mapper.ContentMapper;
import com.util.ChairUtil;

/**
 * 99返利
 * 
 * @author Administrator
 * 
 */
// @Service
@Component
public class fanli99Task {

	@Autowired
	private ContentMapper contentMapper;

	public fanli99Task() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		String tmp = "";
		int p = 1;
		// contentEntityMapper.createTable("20150509");
		while (true) {
			tmp = "http://9.fanli.com/" + "?p=" + p;
			try {
				Document doc = Jsoup.connect(tmp).get();
				String title = doc.title();
				System.out.println("-----------------------------" + p);
				// File input = new
				// File("E:\\myeclipse10Workspaces\\CatchWeb\\WebRoot\\九块邮,返利网九块九包邮独享,全场9.9元包邮促销专区 - 返利网九块邮.htm");
				// Document doc = Jsoup.parse(input, "UTF-8",
				// "E:\\myeclipse10Workspaces\\CatchWeb\\WebRoot\\九块邮,返利网九块九包邮独享,全场9.9元包邮促销专区 - 返利网九块邮.htm");
				Elements link = doc.select(".nine-item").select(".J_panel_mod");
				if (link == null || link.size() == 0) {
					System.out.println("---------------finish------------------");
					return;
				}
				for (Element element : link) {

					Content contentEntity = new Content();
					if (element.getElementsByClass("title").text() == null || "".equals(element.getElementsByClass("title").text())) {
						continue;
					}
					// 商品在返利网的网址+
					System.out.println(element.select("a").attr("href"));
					contentEntity.setWareurl(element.select("a").attr("href"));
					// 商品在返利网跳转到淘宝的网页+
					System.out.println(element.select("a").attr("data-href"));
					contentEntity.setLovetaobaourl(element.select("a").attr("data-href"));
					// 商品图片地址
					System.out.println(element.select("img").attr("data-original"));
					contentEntity.setPictureurl(element.select("img").attr("data-original"));
					// 商品名称+
					System.out.println("商品名称+" + element.getElementsByClass("title").text());
					contentEntity.setWare(element.getElementsByClass("title").text());
					// 购买后状态+
					System.out.println(element.select("i").text());
					contentEntity.setType(element.select("i").text());
					// 已售+
					System.out.println(element.getElementsByClass("amount").text());
					contentEntity.setSold(element.getElementsByClass("amount").text());
					// 优惠价+
					System.out.println(element.select("strong").text());
					contentEntity.setPreferentialprice(Double.parseDouble(element.select("strong").text()));
					// 原价+
					System.out.println(element.select("del").text());

					contentEntity.setCostprice(ChairUtil.dec(12, 15, 2).doubleValue());
					contentEntity.setTurnup(0.0);
					contentEntity.setWebsiteid(1);
					contentEntity.setModelid(1);

					contentEntity.setCreatedate(new Date());
					System.out.println(ChairUtil.char2(contentEntity.getCreatedate(), "ISO0"));
					Map map = new HashMap();
					map.put("date", "20150509");
					map.put("content", contentEntity);

					// String resource = "configuration.xml";
					// Reader reader = Resources.getResourceAsReader(resource);
					// SqlSessionFactory session = new
					// SqlSessionFactoryBuilder().build(reader);
					// SqlSession getSession = session.openSession();
					//
					// ContentMapper contentMapper =
					// getSession.getMapper(ContentMapper.class);
					// ClassPathXmlApplicationContext
					contentMapper.insert(contentEntity);
					// getSession.commit();
				}
				p++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

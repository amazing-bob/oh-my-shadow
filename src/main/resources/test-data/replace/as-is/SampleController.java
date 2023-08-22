package com.sanha.wings.bos.as.as00.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanha.wings.bos.as.as00.controller.setting.as00_0001.AS00_0001_SearchSetting;
import com.wings.biz.cmm.dao.CommDao;

@Controller
public class AS00_0001 {
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	CommDao commDao;
	
	@RequestMapping(value = "/view/sample_01.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String sample_01(HttpServletRequest request, Map<String, Object> commandMap, ModelMap model)throws Exception{
		return "sample/sample_01.jsp";
	}  
	
	/**
	 * CRUD - search list
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view/sample_02.do", method = {RequestMethod.POST})
	public ModelAndView sample_02(HttpServletRequest request, Map<String, Object> commandMap, ModelMap model)throws Exception{
		return SP_02_SearchSetting.getSearchSetting(request, commDao, messageSource, SP_02_SearchSetting.Type.SP_02_search , commandMap).operate();
	}

	@RequestMapping(value = "/view/sample_02.do", method = {RequestMethod.POST})
	public ModelAndView sample_03(HttpServletRequest request,Map<String, Object> commandMap,ModelMap model)throws Exception{
		return SP_02_SearchSetting.getSearchSetting(request, commDao, messageSource, SP_02_SearchSetting.Type.SP_02_search , commandMap).operate();
	}

	@RequestMapping(value = "/view/sample_02.do", method = {RequestMethod.POST})
	public ModelAndView sample_04(HttpServletRequest request , Map<String, Object> commandMap , ModelMap model)throws Exception{
		return SP_QQQQQWWWW_SearchSetting.getSearchSetting(request, commDao, messageSource, SP_QQQQQWW32_SearchSetting.Type.SP_02_search , commandMap).operate();
	}

	@RequestMapping(value = "/view/sample_02.do", method = {RequestMethod.POST})
	public ModelAndView samplee2_05(HttpServletRequest request, Map<String, Object> commandMap , ModelMap model)throws Exception{
		return SP_xx_SearchSetting.getSearchSetting(request, commDao, messageSource, SP_02_SearchSetting.Type.SP_02_search , commandMap).operate();
	}

	@RequestMapping(value = "/view/sample_0.do", method = {RequestMethod.POST})
	public ModelAndView sam_06(HttpServletRequest request	, Map<String, Object> commandMap	, ModelMap model)throws Exception{
		return SP_06_SearchSetting.getSearchSetting(request, commDao, messageSource, SP_06_SearchSetting.Type.SP_06_search , commandMap).operate();
	}

	@RequestMapping(value = "/view/sample_07.do", method = {RequestMethod.POST})
	public ModelAndView sampleqq_07(HttpServletRequest request, Map<String,Object> commandMap, ModelMap model)throws Exception{
		return SP_SearchSetting.getSearchSetting(request, commDao, messageSource, SP__SearchSetting.Type.SP_0_search , commandMap).operate();
	}

	@RequestMapping(value = "/view/sample_08.do", method = {RequestMethod.POST})
	public ModelAndView sampleasd_08(HttpServletRequest request, Map<String,  Object>  commandMap, ModelMap model)throws Exception{
		return SP_SearchSetting.getSearchSetting(request, commDao, messageSource, SP_SearchSetting.Type.SP_0_search , commandMap).operate();
	}
}

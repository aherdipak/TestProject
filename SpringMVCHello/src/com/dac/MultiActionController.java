package com.dac;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/great")
public class MultiActionController {

	@RequestMapping("/Hello.html")
	public ModelAndView helloWorld()
	{
		ModelAndView model=new ModelAndView("HelloPage");
		model.addObject("msg", "Hey User,Welcome");
		return model;
	}
	
	@RequestMapping("/hi.html")
	public ModelAndView hiWorld()
	{
		ModelAndView model=new ModelAndView("HelloPage");
		model.addObject("msg", "Hi World,welcome in Spring World");
		return model;
	}
}

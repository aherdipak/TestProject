package com.dac;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*public class HelloController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		System.out.println("i am in controller");
		ModelAndView model=new ModelAndView("HelloPage");
		model.addObject("msg", "Hi User,Welcome");
		return model;
	}

	
}*/

@Controller
public class HelloController
{
	@RequestMapping("/welcome.html")
	public ModelAndView helloWorld()
	{
		System.out.println("i am in controller.");
		ModelAndView model=new ModelAndView("HelloPage");
		model.addObject("msg", "Hi user,Welcome");
		return model;
	}
	
}

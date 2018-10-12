package de.thewbi.time;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * http://localhost:8080/hello
 *
 */
@Controller
public class ProjectController {

	/**
	 * GET /hello
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(final Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") final String name) {

		System.out.println(new Date());

		model.addAttribute("name", name);

		// this will load, process and return /WEB-INF/views/<value>.jsp
//		return "hello";
		return "test";
	}

}

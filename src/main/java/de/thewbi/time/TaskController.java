package de.thewbi.time;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	/**
	 * GET /index
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
	public String index(final Model model, @PathVariable final Long projectId) {

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "tasks";
	}
}

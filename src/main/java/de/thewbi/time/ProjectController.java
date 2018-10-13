package de.thewbi.time;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.thewbi.time.data.Entry;
import de.thewbi.time.data.EntryType;
import de.thewbi.time.facade.ProjectFacade;

/**
 * http://localhost:8080/projects
 *
 */
@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectFacade projectFacade;

	/**
	 * GET /index
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(final Model model) {

		if (!model.containsAttribute("errorMsg")) {
			model.addAttribute("errorMsg", "");
		}
		if (!model.containsAttribute("projectCreatedMsg")) {
			model.addAttribute("projectCreatedMsg", "");
		}
		if (!model.containsAttribute("entry")) {
			model.addAttribute("entry", new Entry());
		}

		final List<Entry> projects = projectFacade.getProjects();
		model.addAttribute("projects", projects);

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "projects";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProject(@Valid @ModelAttribute("entry") final Entry entry, final BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("errorMsg", "add.project.failed");
			redirectAttributes.addFlashAttribute("entry", entry);

			return "redirect:/projects";
		}

		// make it a project
		entry.setEntryType(EntryType.PROJECT);

		try {
			projectFacade.saveProject(entry);
			redirectAttributes.addFlashAttribute("projectCreatedMsg", "add.project.success");
		} catch (final RuntimeException e) {

			redirectAttributes.addFlashAttribute("errorMsg", "add.project.failed");
			redirectAttributes.addFlashAttribute("entry", entry);

			return "redirect:/projects";
		}

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "redirect:/projects";
	}

}

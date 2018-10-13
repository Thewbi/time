package de.thewbi.time;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.thewbi.time.data.Entry;
import de.thewbi.time.data.EntryType;
import de.thewbi.time.facade.ProjectFacade;
import de.thewbi.time.facade.TaskFacade;

/**
 * http://localhost:8080/projects
 *
 */
@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectFacade projectFacade;

	@Autowired
	private TaskFacade taskFacade;

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

	/**
	 * GET /projects/{projectid}
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
	public String project(final Model model, @PathVariable final Long projectId) {

		final Optional<Entry> projectOptional = projectFacade.getProjectById(projectId);
		if (!projectOptional.isPresent()) {
			model.addAttribute("errorMsg", "project.doesnotexist");

			// this will load, process and return /WEB-INF/views/<value>.jsp
			return "tasks";
		}

		final Entry project = projectOptional.get();

		model.addAttribute("project", project);
		model.addAttribute("tasks", project.getChildren());

		if (!model.containsAttribute("errorMsg")) {
			model.addAttribute("errorMsg", "");
		}
		if (!model.containsAttribute("taskCreatedMsg")) {
			model.addAttribute("taskCreatedMsg", "");
		}
		if (!model.containsAttribute("entry")) {
			model.addAttribute("entry", new Entry());
		}

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "project";
	}

	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
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
		return "redirect:/projects/" + entry.getId();
	}

	@RequestMapping(value = "/addTaskToProject", method = RequestMethod.POST)
	public String addTaskToProject(@Valid @ModelAttribute("entry") final Entry task, final BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("errorMsg", "add.task.failed");
			redirectAttributes.addFlashAttribute("entry", task);

			return "redirect:/projects";
		}

		// make it a task
		task.setEntryType(EntryType.TASK);

		final Optional<Entry> projectOptional = projectFacade.getProjectById(task.getTempParentId());
		if (!projectOptional.isPresent()) {
			redirectAttributes.addAttribute("errorMsg", "project.doesnotexist");

			// this will load, process and return /WEB-INF/views/<value>.jsp
			return "redirect:/projects";
		}

		final Entry project = projectOptional.get();

		try {
			project.getChildren().add(task);
			task.setParent(project);

			// parent first, then child
			projectFacade.saveProject(projectOptional.get());
			taskFacade.saveTask(task);
			redirectAttributes.addFlashAttribute("taskCreatedMsg", "add.task.success");
		} catch (final RuntimeException e) {

			redirectAttributes.addFlashAttribute("errorMsg", "add.task.failed");
			redirectAttributes.addFlashAttribute("entry", task);

			return "redirect:/projects";
		}

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "redirect:/tasks/" + task.getId();
	}

}

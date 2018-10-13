package de.thewbi.time;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.thewbi.time.data.Entry;
import de.thewbi.time.data.EntryType;
import de.thewbi.time.facade.DurationFacade;
import de.thewbi.time.facade.TaskFacade;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskFacade taskFacade;

	@Autowired
	private DurationFacade durationFacade;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Timestamp.class, new TimeStampEditor());
	}

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

		final List<Entry> tasks = taskFacade.getTasks();
		model.addAttribute("tasks", tasks);

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "tasks";
	}

	/**
	 * GET /index
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
	public String index(final Model model, @PathVariable final Long taskId) {

		final Optional<Entry> taskOptional = taskFacade.getTaskById(taskId);
		if (!taskOptional.isPresent()) {
			model.addAttribute("errorMsg", "task.doesnotexist");

			// this will load, process and return /WEB-INF/views/<value>.jsp
			return "tasks";
		}

		final Entry task = taskOptional.get();

		model.addAttribute("task", task);
		model.addAttribute("durations", task.getChildren());

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
		return "task";
	}

	@RequestMapping(value = "/addDurationToTask", method = RequestMethod.POST)
	public String addDurationToTask(@Valid @ModelAttribute("entry") final Entry duration,
			final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("errorMsg", "add.task.failed");
			redirectAttributes.addFlashAttribute("entry", duration);

			return "redirect:/task";
		}

		// make it a duration
		duration.setEntryType(EntryType.DURATION);

		final Optional<Entry> taskOptional = taskFacade.getTaskById(duration.getTempParentId());
		if (!taskOptional.isPresent()) {
			redirectAttributes.addAttribute("errorMsg", "task.doesnotexist");

			// this will load, process and return /WEB-INF/views/<value>.jsp
			return "redirect:/task";
		}

		final Entry task = taskOptional.get();

		try {
			task.getChildren().add(duration);
			duration.setParent(task);

			// parent first, then child
			taskFacade.saveTask(taskOptional.get());
			durationFacade.saveDuration(duration);
			redirectAttributes.addFlashAttribute("durationCreatedMsg", "add.duration.success");
		} catch (final RuntimeException e) {

			redirectAttributes.addFlashAttribute("errorMsg", "add.duration.failed");
			redirectAttributes.addFlashAttribute("entry", task);

			return "redirect:/tasks/" + task.getId();
		}

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "redirect:/tasks/" + task.getId();
	}

}

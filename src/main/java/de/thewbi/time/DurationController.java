package de.thewbi.time;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.thewbi.time.data.Entry;
import de.thewbi.time.facade.DurationFacade;

@Controller
@RequestMapping("/durations")
public class DurationController {

	@Autowired
	private DurationFacade durationFacade;

	@RequestMapping(value = "/delete/{durationId}", method = RequestMethod.GET)
	public String delete(final Model model, @PathVariable final Long durationId) {

		final Optional<Entry> durationOptional = durationFacade.getDurationById(durationId);

		if (durationOptional.isPresent()) {
			durationFacade.delete(durationOptional.get());

			return "redirect:/tasks/" + durationOptional.get().getParent().getId();
		}

		return "redirect:/tasks";
	}

}

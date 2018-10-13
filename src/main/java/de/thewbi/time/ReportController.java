package de.thewbi.time;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.thewbi.time.data.Entry;
import de.thewbi.time.facade.DurationFacade;

@Controller
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	private DurationFacade durationFacade;

	@RequestMapping(value = "/weekly", method = RequestMethod.GET)
	public String weeklyReport(final Model model) {

		final List<Entry> durations = durationFacade.getDurationsForWeeklyReport();
		model.addAttribute("durations", durations);

		// this will load, process and return /WEB-INF/views/<value>.jsp
		return "weeklyreport";
	}

}

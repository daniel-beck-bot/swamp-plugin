
/* 
SWAMP Jenkins Plugin

Copyright 2016 Jared Sweetland, Vamshi Basupalli, James A. Kupsch

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.continuousassurance.swamp.jenkins;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.continuousassurance.scarf.parser.ScarfInterface;
import org.continuousassurance.scarf.parser.ScarfXmlReader;
import org.continuousassurance.scarf.datastructures.*;

public class ScarfParser implements ScarfInterface {
	/**
	 * Initial info from SCARF file
	 */
	private InitialInfo info;
	/**
	 * List of bugs in the SCARF file
	 */
	private List<BugInstance> bugs;
	/**
	 * List of metrics in the SCARF file
	 */
	private List<Metric> metrics;
	/**
	 * List of metric summaries in the SCARF file
	 */
	private List<MetricSummary> metricSummaries;
	/**
	 * List of bug summaries in the SCARF file
	 */
	private List<BugSummary> bugSummaries;
	/**
	 * Name of tool that ran assessment
	 */
	private String tool;
	/**
	 * Name of platform on which assessment was run
	 */
	private String platform;
	
	static final String PKG1 = "pkg1";
	
	/**
	 * Constructor for ResultsParser
	 * @param f SCARF file being read
	 */
	public ScarfParser(File f) {
		ScarfXmlReader reader = new ScarfXmlReader(this);
		bugs = new ArrayList<>();
		metrics = new ArrayList<>();
		metricSummaries = new ArrayList<>();
		bugSummaries = new ArrayList<>();
		reader.parseFromFile(f);
	}
	
	@Override
	/**
	 * Callback for initial info in SCARF
	 * @param initial InitialInfo object
	 */
	public void initialCallback(InitialInfo initial) {
		info = initial;
		tool = info.getToolName();
		System.out.println("Initial call back - " + initial.getUUID());
		platform = "?"; // TODO: Get the actual Platform once SCARF is updated
	}
	
	@Override
	/**
	 * Callback for metric in SCARF
	 * @param metric Metric that was just parsed from SCARF file
	 */
	public void metricCallback(Metric metric) {
		System.out.println("Metric added: " + metric.getMetricType() + " - " + metric.getMetricValue());
		metrics.add(metric);
	}
	
	@Override
	/**
	 * Callback for metric summary in SCARF
	 * @param summary MetricSummary that was just parsed from SCARF file
	 */
	public void metricSummaryCallback(MetricSummary summary) {
		System.out.println("Metric Summary added: " + summary.getMetricType() + " - " + summary.getMin() + " to " + summary.getMax());
		metricSummaries.add(summary);
	}

	@Override
	/**
	 * Callback for bug in SCARF
	 * @param bug BugInstance that was just parsed from SCARF file
	 */
	public void bugCallback(BugInstance bug) {
		for (Location loc : bug.getLocations()) {
			String src_file = loc.getSourceFile();
			if (src_file.startsWith(PKG1)){
				loc.setSourceFile(src_file.substring(PKG1.length() + 1));
			}
		}
		
		bugs.add(bug);
	}
	
	@Override
	/**
	 * Callback for bug summaries in SCARF
	 * @param summary BugSummary that was just parsed from the SCARF file
	 */
	public void bugSummaryCallback(BugSummary summary) {
		bugSummaries.add(summary);
	}
	
	/**
	 * Getter for tool name
	 * @return name of tool that ran the assessment
	 */
	public String getToolName() {
		return tool;
	}
	
	/**
	 * Getter for platform name
	 * @return name of platform assessment was run on
	 */
	public String getPlatformName() {
		return platform;
	}

	/**
	 * Getter for list of bugs
	 * @return a list of BugInstance objects
	 */
	public List<BugInstance> getAllBugInstances() {
		return bugs;
	}

	public void finalCallback() {
		System.out.println("Final callback");
	}

}

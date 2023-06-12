/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.springframework.cli.runtime.engine.actions.handlers;

import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cli.runtime.engine.actions.PomUpdate;
import org.springframework.cli.runtime.engine.templating.TemplateEngine;
import org.springframework.cli.util.PomUpdateUtils;
import org.springframework.cli.util.ProjectInfo;
import org.springframework.cli.util.TerminalMessage;

public class PomUpdateActionHandler {

	private static final Logger logger = LoggerFactory.getLogger(InjectMavenDependencyActionHandler.class);
	private final TemplateEngine templateEngine;
	private Map<String, Object> model;
	private final Path cwd;
	private final TerminalMessage terminalMessage;
	public PomUpdateActionHandler(TemplateEngine templateEngine, Map<String, Object> model, Path cwd, TerminalMessage terminalMessage) {
		this.templateEngine = templateEngine;
		this.model =  model;
		this.cwd = cwd;
		this.terminalMessage = terminalMessage;
	}

	public void execute(PomUpdate pomUpdate) {
		PomUpdateUtils pomUpdateUtils = new PomUpdateUtils();
		//TODO check empty valures
		String rawDescription = pomUpdate.getProjectDescription();
		String projectDescription = templateEngine.process(rawDescription, model);
		String rawName = pomUpdate.getProjectName();
		String projectName = templateEngine.process(rawName, model);
		String rawVersion = pomUpdate.getProjectVersion();
		String projectVersion = templateEngine.process(rawVersion, model);


		ProjectInfo projectInfo = new ProjectInfo("", "",
				projectVersion,
				projectName,
				projectDescription,
				"");
		pomUpdateUtils.updatePom(cwd, projectInfo);

		logger.debug("Updated pom.xml");
	}
}

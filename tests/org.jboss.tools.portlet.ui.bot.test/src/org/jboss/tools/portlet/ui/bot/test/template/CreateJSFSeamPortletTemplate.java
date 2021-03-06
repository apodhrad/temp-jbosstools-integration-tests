package org.jboss.tools.portlet.ui.bot.test.template;

import java.util.Arrays;
import java.util.List;

import org.jboss.tools.portlet.ui.bot.entity.XMLNode;
import org.jboss.tools.portlet.ui.bot.task.wizard.WizardPageDefaultsFillingTask;
import org.jboss.tools.portlet.ui.bot.task.wizard.web.jboss.AbstractPortletCreationTask;
import org.jboss.tools.portlet.ui.bot.task.wizard.web.jboss.JSFPortletCreationTask;

/**
 * Creates a new jsf portlet and checks if the right files are generated.  
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class CreateJSFSeamPortletTemplate extends CreatePortletTemplate {

	private static final String FACES_CLASS_NAME = "javax.portlet.faces.GenericFacesPortlet";

	@Override
	protected AbstractPortletCreationTask getCreatePortletTask() {
		JSFPortletCreationTask task = new JSFPortletCreationTask();
		task.addWizardPage(new WizardPageDefaultsFillingTask());
		task.addWizardPage(new WizardPageDefaultsFillingTask());
		task.addWizardPage(new WizardPageDefaultsFillingTask());
		return task;
	}

	@Override
	protected List<XMLNode> getExpectedXMLNodes() {
		return Arrays.asList(new XMLNode("portlet-app/portlet/portlet-class", FACES_CLASS_NAME));
	}
}

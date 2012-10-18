package org.jboss.tools.bpel.ui.bot.test;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.jboss.tools.bpel.ui.bot.test.util.ResourceHelper;
import org.jboss.tools.bpel.util.SendSoapMessage;
import org.jboss.tools.ui.bot.ext.config.Annotations.Require;
import org.jboss.tools.ui.bot.ext.config.Annotations.Server;
import org.jboss.tools.ui.bot.ext.config.Annotations.ServerState;
import org.jboss.tools.ui.bot.ext.config.Annotations.ServerType;
import org.jboss.tools.ui.bot.ext.view.ServersView;
import org.jboss.tools.ui.bot.ext.wizards.SWTBotImportWizard;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author psrna, apodhrad
 * 
 */
@Require(server = @Server(type = ServerType.ALL, state = ServerState.Running), perspective = "BPEL")
public class DeploymentDescriptorTest extends BPELTest {

	final static String BUNDLE = "org.jboss.tools.bpel.ui.bot.test";
	final static String ENDPOINT = "http://localhost:8080/SayHelloProcess";
	final static String MESSAGE = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
			+ "xmlns:q0=\"http://www.jboss.org/bpel/examples\" "
			+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"  "
			+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
			+ "	<soapenv:Header/>"
			+ "	<soapenv:Body>"
			+ "			<q0:SayHelloRequest>"
			+ "				<q0:input>JBDS</q0:input>"
			+ "			</q0:SayHelloRequest>" + "	</soapenv:Body>" + "</soapenv:Envelope>";

	final static String EXPECTED_RESPONSE = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "  <SOAP-ENV:Header />"
			+ "  <SOAP-ENV:Body>"
			+ "    <SayHelloResponse xmlns=\"http://www.jboss.org/bpel/examples\">"
			+ "      <tns:result xmlns:tns=\"http://www.jboss.org/bpel/examples\">Hello JBDS</tns:result>"
			+ "    </SayHelloResponse>" + "  </SOAP-ENV:Body>" + "</SOAP-ENV:Envelope>";

	ServersView sView = new ServersView();

	@BeforeClass
	public static void setupWorkspace() throws Exception {
		ResourceHelper.importProject(BUNDLE, "/projects/bpel_say_hello", "say_hello");
		bot.viewByTitle("Project Explorer").setFocus();
	}

	@Test
	public void deploymentDescriptorTest() throws Exception {

		IFile deployFile = createNewDeployDescriptor("say_hello");

		bot.editorByTitle("deploy.xml").show();
		bot.editorByTitle("deploy.xml").setFocus();
		SWTBot editorBot = bot.editorByTitle("deploy.xml").bot();

		SWTBotTable table = editorBot.table(0);
		table.click(0, 1);
		bot.sleep(TIME_1S);

		editorBot.ccomboBox("-- none -- ").setSelection("SayHelloPort");
		table.click(0, 2);
		bot.editorByTitle("deploy.xml").save();

		bot.sleep(TIME_10S);

	}

	@Test
	public void editDeploymentDescriptorTest() {
		// Import wsdl
		SWTBotImportWizard wizard = new SWTBotImportWizard();
		wizard.open("File System", "General");
		String path = org.jboss.tools.ui.bot.ext.helper.ResourceHelper.getResourceAbsolutePath(
				BUNDLE, "resources", "wsdl");
		wizard.bot().comboBoxWithLabel("From directory:").typeText(path);
		wizard.bot().tree().setFocus();
		wizard.bot().tree().getTreeItem("wsdl").check();
		wizard.bot().textWithLabel("Into folder:").setText("say_hello/bpelContent");
		wizard.bot().button("Finish").click();
		
		System.out.println("ok");
	}

}
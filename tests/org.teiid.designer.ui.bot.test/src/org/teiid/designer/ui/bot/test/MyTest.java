package org.teiid.designer.ui.bot.test;

import org.jboss.tools.ui.bot.ext.config.Annotations.Require;
import org.jboss.tools.ui.bot.ext.config.Annotations.Server;
import org.jboss.tools.ui.bot.ext.config.Annotations.ServerState;
import org.jboss.tools.ui.bot.ext.config.Annotations.ServerType;
import org.jboss.tools.ui.bot.ext.helper.ImportHelper;
import org.jboss.tools.ui.bot.ext.helper.ResourceHelper;
import org.junit.Test;
import org.teiid.designer.ui.bot.ext.teiid.editor.ModelEditor;
import org.teiid.designer.ui.bot.ext.teiid.perspective.TeiidPerspective;
import org.teiid.designer.ui.bot.ext.teiid.view.ModelExplorerView;
import org.teiid.designer.ui.bot.test.suite.TeiidDesignerTestCase;

/**
 * 
 * @author apodhrad
 * 
 */
@Require(server = @Server(type = ServerType.ALL, state = ServerState.Present), perspective = "Teiid Designer")
public class MyTest extends TeiidDesignerTestCase {

	@Test
	public void myTest() {
		String path = ResourceHelper.getResourceAbsolutePath("org.teiid.designer.ui.bot.test", "resources", "ModeShapeGoodies.zip");
		ImportHelper.importProjectFromZip(path);

		test1();
		test2();
	}

	private static void openMappingDiagram() {
		ModelExplorerView modelExplorer = TeiidPerspective.getInstance().getModelExplorerView();

		modelExplorer.open("ModeShapeGoodies", "BooksDoc.xmi", "bookListingDocument",
				"Mapping Diagram");
	}

	private static void openTransformationDiagram() {
		ModelExplorerView modelExplorer = TeiidPerspective.getInstance().getModelExplorerView();

		modelExplorer.open("ModeShapeGoodies", "RelModels", "BooksInfo.xmi", "bookInfo",
				"Transformation Diagram");
	}

	private static void test1() {
		openTransformationDiagram();

		ModelEditor modelEditor = modelEditor("BooksInfo.xmi");
		modelEditor.show();
		modelEditor.showTransformation();
		modelEditor.setTransformation("Test SQL 1");
		modelEditor.saveAndClose();
	}

	private static void test2() {
		openMappingDiagram();

		ModelEditor modelEditor = modelEditor("BooksDoc.xmi");
		modelEditor.show();
		modelEditor.showMappingTransformation("book");
		modelEditor.setTransformation("Test SQL 2");
		modelEditor.saveAndClose();
	}

}
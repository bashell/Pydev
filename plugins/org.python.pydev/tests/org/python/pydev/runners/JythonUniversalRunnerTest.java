/**
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Eclipse Public License (EPL).
 * Please see the license.txt included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package org.python.pydev.runners;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.python.pydev.core.IPythonNature;
import org.python.pydev.core.TestDependent;
import org.python.pydev.core.Tuple;
import org.python.pydev.editor.codecompletion.revisited.jython.JythonCodeCompletionTestsBase;
import org.python.pydev.editor.codecompletion.revisited.modules.CompiledModule;
import org.python.pydev.runners.UniversalRunner.AbstractRunner;

public class JythonUniversalRunnerTest extends JythonCodeCompletionTestsBase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(JythonUniversalRunnerTest.class);
    }

    
    @Override
    public void setUp() throws Exception {
    	super.setUp();
        CompiledModule.COMPILED_MODULES_ENABLED = true;
        this.restorePythonPath(TestDependent.JYTHON_LIB_LOCATION+"|"+TestDependent.JAVA_RT_JAR_LOCATION, false);
    }

    @Override
    public void tearDown() throws Exception {
        CompiledModule.COMPILED_MODULES_ENABLED = false;
        super.tearDown();
    }
    
    public void testUniversalRunnerWithJython() throws Exception {
		AbstractRunner runner = UniversalRunner.getRunner(nature);
		assertEquals(nature.getInterpreterType(), IPythonNature.INTERPRETER_TYPE_JYTHON);
		Tuple<String, String> output = runner.runCodeAndGetOutput(
				"import sys\nprint 'test'\nprint >> sys.stderr, 'err'", null, null, new NullProgressMonitor());
		assertEquals("test", output.o1.trim());
		assertEquals("err", output.o2.trim());
		
		Tuple<Process, String> createProcess = 
			runner.createProcess(TestDependent.TEST_PYSRC_LOC+"universal_runner_test.py", null, null, new NullProgressMonitor());
		output = SimpleRunner.getProcessOutput(createProcess.o1, "", new NullProgressMonitor());
		assertEquals("stdout", output.o1.trim());
		assertEquals("stderr", output.o2.trim());
	}
    
}

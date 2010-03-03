package gov.nih.nci.ncicb.cadsr.rules;

import junit.framework.Test;
import junit.framework.TestSuite;


public class RulesTestCaseRunner  {

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(ValueDomainNoErrorTestCase.class);
		suite.addTestSuite(ValueDomainErrorTestCase.class);
		suite.addTestSuite(DECNoErrorTestCase.class);
		
		return suite;
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}

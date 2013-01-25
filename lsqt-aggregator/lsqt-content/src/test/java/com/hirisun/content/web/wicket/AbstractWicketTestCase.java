package com.hirisun.content.web.wicket;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;

public class AbstractWicketTestCase {
	/***/
	protected WicketTester tester;

	/**
	 * 
	 */
	@Before
	public void before()
	{
		tester = new WicketTester();
	}

	/**
	 * 
	 */
	@After
	public void after()
	{
		tester.destroy();
	}

}

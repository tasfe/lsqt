package com.hirisun;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.hirisun.content.service.ServiceTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ServiceTestSuite.class})
public class AllTestSuite {

}

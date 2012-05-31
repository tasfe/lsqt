package com.hirisun;
/**
 * 菩萨蛮
 *			韦庄
 *	人人尽说江南好，游人只合江南老。
 *	春水碧于天，画船听枕眠。
 *	垆边人似月，皓腕凝霜雪。
 *	未老莫还乡，还乡须断肠。
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.hirisun.content.service.ServiceTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ServiceTestSuite.class})
public class AllTestSuite {

}

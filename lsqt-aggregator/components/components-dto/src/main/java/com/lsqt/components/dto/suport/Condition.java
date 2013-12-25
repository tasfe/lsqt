package com.lsqt.components.dto.suport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import java.util.Map;


/**
 * 分页时用到的where条件Bean
 * 调用范例:
 * 		Page<User> page=new Page<User>(1,1);
 *		page.addConditions(Condition.getInstance().like("name", "张",MatchWay.ANYWHERE).in("name", new Object[]{"王"}));
 *		page.addOrderProperties("name", true);
 *		page = userService.loadPage(page);
 *
 * @author 袁明敏
 * @since 1.0
 *
 */

public final class Condition{
	private Condition() {}

	
}

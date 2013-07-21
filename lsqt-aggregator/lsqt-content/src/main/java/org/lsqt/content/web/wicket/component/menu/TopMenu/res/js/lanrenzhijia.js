var get = {
	byId: function(id) {
		return document.getElementById(id)
	},
	byClass: function(sClass, oParent) {
		var aClass = [];
		var reClass = new RegExp("(^| )" + sClass + "( |$)");
		var aElem = this.byTagName("*", oParent);
		for (var i = 0; i < aElem.length; i++) reClass.test(aElem[i].className) && aClass.push(aElem[i]);
		return aClass
	},
	byTagName: function(elem, obj) {
		return (obj || document).getElementsByTagName(elem)
	}
};
window.onload = function ()
{
	var oNav = get.byId("nav");
	var aLi = get.byTagName("li", oNav);
	var aSubNav = get.byClass("subnav", oNav);
	var oSubNav = oEm = timer = null;
	var i = 0;
	
	for (i = 1; i < aLi.length; i++)
	{
		aLi[i].onmouseover = function ()
		{
			//隐藏所有子菜单
			for (i = 0; i < aSubNav.length; i++)aSubNav[i].style.display = "none";
			
			//获取该项下的子菜单
			oSubNav = get.byClass("subnav", this)[0];
			
			//获取该项下的指示箭头
			oEm = get.byTagName("em", this)[0];
			
			//显示该项下的子菜单
			oSubNav.style.display = "block";
			
			//判断显示区域
			oNav.offsetWidth - this.offsetLeft > oSubNav.offsetWidth ? 
			
			//如果在显示范围居左显示
			oSubNav.style.left = this.offsetLeft + "px" :
			
			//超出显示范围居右显示
			oSubNav.style.right = 0;
			
			//计算指标箭头显示位置
			oEm.style.left = this.offsetLeft - oSubNav.offsetLeft + 50 + "px";
			clearTimeout(timer);
			
			//阻止事件冒泡
			oSubNav.onmouseover = function (event)
			{
				(event || window.event).cancelBubble = true;
				clearTimeout(timer)
			}
		};
		
		aLi[i].onmouseout = function ()
		{
			timer = setTimeout(function () {
				oSubNav.style.display = "none"
			},300)	
		}
	}
};
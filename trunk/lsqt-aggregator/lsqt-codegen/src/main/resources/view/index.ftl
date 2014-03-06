
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    
    <script src="http://localhost:8080/lsqt-static/miniui/boot.js" type="text/javascript"></script>

    <script src="http://localhost:8080/lsqt-static/miniui/core.js" type="text/javascript"></script>

    <style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    .header
    {
        background:url(http://localhost:8080/lsqt-static/miniui/demo/header.gif) repeat-x;
    }
    .header div
    {
        font-family:'Trebuchet MS',Arial,sans-serif;
        font-size:25px;line-height:60px;padding-left:10px;font-weight:bold;color:#333;        
    }    
    body .header .topNav
    {
        position:absolute;right:8px;top:10px;        
        font-size:12px;
        line-height:25px;
    }
    .header .topNav a
    {
        text-decoration:none;
        color:#222;
        font-weight:normal;
        font-size:12px;
        line-height:25px;
        margin-left:3px;
        margin-right:3px;
    }
    .header .topNav a:hover
    {
        text-decoration:underline;
        color:Blue;
    }   
     .mini-layout-region-south img
    {
        vertical-align:top;
    }
    </style>
</head>
<body>
<div class="mini-layout" style="width:100%;height:100%;">
    <div title="north" region="north" class="header" bodyStyle="overflow:hidden;" height="80" showHeader="false" showSplit="false">
        <div>test</div>

        <div class="topNav">    
            <a href="../index.html">首页</a> |
            <a href="../demo/index.html">在线示例</a> |
            <a href="../docs/api/index.html">Api手册</a> |            
            <a href="../docs/tutorial/index.html">开发教程</a> |
            <a href="../docs/quickstart/index.html">快速入门</a>
        </div>

        <div style="position:absolute;right:12px;bottom:5px;font-size:12px;line-height:25px;font-weight:normal;">
            <span style="color:Red;font-family:Tahoma">（推荐Blue）</span>选择皮肤：
            <select id="selectSkin" onchange="onSkinChange(this.value)" style="width:100px;" >
                <option value="">Default</option>
                <option value="blue">Blue</option>
                <option value="gray">Gray</option>
                <option value="olive2003">Olive2003</option>
                <option value="blue2003">Blue2003</option>
                <option value="blue2010">Blue2010</option>
            </select>
        </div>
    </div>
    <div showHeader="false" region="south" style="border:0;text-align:center;" height="25" showSplit="false">
        Copyright © 前海股权交易中心
    </div>
    <div region="west" title="导航菜单" showHeader="true" bodyStyle="padding-left:1px;" showSplitIcon="true" width="230" minWidth="100" maxWidth="350">
         <div id="leftTree" class="mini-outlookmenu" url="http://localhost:8080/lsqt-static/apps/codegen/js/outlookmenu.txt" onitemselect="onItemSelect"
            idField="id" parentField="pid" textField="text" borderStyle="border:0" 
        >
        </div>
         
    </div>
    <div title="center" region="center"  bodyStyle="overflow:hidden;">
		<iframe id="mainframe" frameborder="0" name="main" style="width:100%;height:100%;" border="0"></iframe>
    </div>

    
</div>
</body>
</html>
<script type="text/javascript">
    mini.parse();
     
    var iframe = document.getElementById("mainframe");
    
    function onBeforeExpand(e) {
        var tree = e.sender;
        var nowNode = e.node;
        var level = tree.getLevel(nowNode);

        var root = tree.getRootNode();        
        tree.cascadeChild(root, function (node) {
            if (tree.isExpandedNode(node)) {
                var level2 = tree.getLevel(node);
                if (node != nowNode && !tree.isAncestor(node, nowNode) && level == level2) {
                    tree.collapseNode(node, true);
                }
            }
        });

    }
    
	function onItemSelect(e) {
        var item = e.item;
        iframe.src = item.url;
    }
	
</script>


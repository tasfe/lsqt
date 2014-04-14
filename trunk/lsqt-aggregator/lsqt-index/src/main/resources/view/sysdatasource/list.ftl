<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    
    <script src="http://localhost:8080/lsqt-static/miniui/boot.js" type="text/javascript"></script>
    
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
<head>
<body>
<div class="mini-layout" style="width:100%;height:100%;">
    <div title="center" region="center"  bodyStyle="overflow:hidden;">
		<div class="mini-toolbar" style="padding:2px;border-bottom:0;">
	        <table style="width:100%;">
	            <tr>
	            <td style="width:100%;">
	                <a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增</a>
	                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
	                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-reload" plain="true">刷新</a>
	            </td>
	            <td style="white-space:nowrap;"><label style="font-family:Verdana;">关键字: </label>
	                <input class="mini-textbox" />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="onSearch()">查询</a>
	                </td>
	            </tr>
	        </table>
	    </div>
	    <div class="mini-fit" >
	        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
	            url="${ctx}/sysDataSource/list"  idField="ID" sortMode="client"
	            sizeList="[5,10,20,50]" pageSize="20" multiSelect="true">
	            <div property="columns">
	            	<div type="checkcolumn" ></div>    
	                <!--<div type="indexcolumn" ></div>-->
	                <div field="id" width="120" headerAlign="center" allowSort="true">流水号</div>    
	                <div field="alias" width="120" headerAlign="center" allowSort="true">别名</div>                            
	                <div field="dbType" width="100" renderer="onGenderRenderer" align="center" headerAlign="center">数据库类型</div>
	                <div field="driverName" width="100" allowSort="true">驱动名称</div>                                    
	                <div field="name" width="100" allowSort="true">数据库实例名</div>
	                <div field="url" width="100" headerAlign="center" allowSort="true">数据库链接</div>                
	            </div>
	        </div> 
	
	    </div>
    </div>
</div>
<script>
		mini.parse();
		var grid = mini.get("datagrid1");
    	grid.load();

        function add() {
            
            mini.open({
                url: "${ctx}/sysDataSource/edit",
                title: "新增", width: 600, height: 360,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                
                    grid.reload();
                }
            });
        }
        function edit() {
         
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "${ctx}/sysDataSource/edit",
                    title: "编辑数据源", width: 600, height: 360,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.id };
                        iframe.contentWindow.SetData(data);
                        
                    },
                    ondestroy: function (action) {
                        grid.reload();
                        
                    }
                });
                
            } else {
                alert("请选中一条记录");
            }
            
        }
        function remove() {
            
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.id);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "${ctx}/sysDataSource/delete/" +id,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        } 
</script>
</body>
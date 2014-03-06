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
    <div region="west" title="查询菜单" showHeader="true" bodyStyle="padding-left:1px;" showSplitIcon="true" width="230" minWidth="100" maxWidth="350">

    </div>
    
    <div title="center" region="center"  bodyStyle="overflow:hidden;">
		<div class="mini-toolbar" style="padding:2px;border-bottom:0;">
	        <table style="width:100%;">
	            <tr>
	            <td style="width:100%;">
	                <a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增表</a>
	                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑表</a>
	                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="editColumn()">编辑表字段</a>
	                 
	                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData()">保存</a>
	                <a class="mini-button" iconCls="icon-add" plain="true" onclick="copy()">复制</a>
	               
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-reload" plain="true">刷新</a>
	            </td>
	            <td style="white-space:nowrap;">
	            	数据源：<input name="dsId" showNullItem="true" class="mini-combobox" url="${ctx}/sysDataSource/listCombobox" value="cn" textField="text" valueField="id" />
	                <input class="mini-textbox" />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="onSearch()">查询</a>
	                </td>
	            </tr>
	        </table>
	    </div>
	    <div class="mini-fit" >
	        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
	            url="${ctx}/bpmFormTable/list"  idField="ID" sortMode="client"
	            allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
       			editNextOnEnterKey="true"  editNextRowCell="true"
	            sizeList="[5,10,20,50]" pageSize="20" multiSelect="true">
	            <div property="columns">
	            	<div type="checkcolumn" ></div>
	            	<!--   
	                <div type="indexcolumn" ></div>
	                 --> 
	                <div field="tableId" width="120" headerAlign="center" allowSort="true">流水号</div>    
	                <div field="dsName" width="120" headerAlign="center" allowSort="true">数据源</div>
	                <div field="tableName" width="120" headerAlign="center" allowSort="true">表名
	                 	<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
	                </div>  
	                <div field="entityName" width="150" headerAlign="center" allowSort="true">实体名称
	                 	<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
	                </div>  
	                <div field="tableDesc" width="100" align="center" headerAlign="center">备注
	                	<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
	                </div>
	                <div field="versionNo" width="100" allowSort="true">版本号</div>                                    
	                <div field="isPublished" width="100" allowSort="true" renderer="onPublishedRenderer">是否发布</div>
	                
	                <div field="publishedBy" width="100" allowSort="true">发布人</div>
	                <div field="publishTime" width="100" headerAlign="center" allowSort="true">发布时间</div>
	            </div>
	        </div> 
	
	    </div>
    </div>
</div>
<script>
		mini.parse();
		var grid = mini.get("datagrid1");
    	grid.load();
    	
    	function editColumn(){ //编辑表字段
    		 var row = grid.getSelected();
            if (row) {
            	window.open("${ctx}/bpmFormField/list/"+row.tableId,"_self");
            }
    	} 
    	 
		function addColumn() { //新增字段
         
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "${ctx}/sysDataSource/edit",
                    title: "编辑数据源", width: 600, height: 360,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.tableId };
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
        
        function edit() {
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "${ctx}/bpmFormTable/edit",
                    title: "编辑表定义", width: 600, height: 360,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.tableId };
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
        function add() {
            
            mini.open({
                url: "${ctx}/bpmFormTable/edit",
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
                        url: "${ctx}/datasource/delete/ids=" +id,
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
        
        function onPublishedRenderer(e) {
            if (e.value == 1) return "<font color='green'>已发布</fornt>";
            else return "<font color='red'>未发布</font>";
        }
        
        function saveData() {
            
            var data = grid.getChanges();
            var json = mini.encode(data);
            
            grid.loading("保存中，请稍后......");
            $.ajax({
                url: "${ctx}/bpmFormTable/batchSave",
                data: { data: json },
                type: "post",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }
       
</script>
</body>
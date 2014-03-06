<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>面板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="http://localhost:8080/lsqt-static/miniui/boot.js" type="text/javascript"></script>
    
    <style type="text/css">
    html, body
    {
        font-size:12px;
        padding:0;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden;
    }
    </style>
</head>
<body>    
     
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
                <tr>
                    <td style="width:100px;">表名称：</td>
                    <td style="width:150px;">    
                    	 <input name="tableId" class="mini-textbox" style="display:none;"/>
                        <input name="tableName" class="mini-textbox" required="true"  emptyText="请输入表名称"/>
                    </td>
                    <td style="width:100px;">注释：</td>
                    <td style="width:150px;">
                         <input name="tableDesc" class="mini-textbox"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:100px;">所属数据源：</td>
                    <td style="width:150px;">    
                       <input name="dsId" showNullItem="false"  class="mini-combobox" url="${ctx}/sysDataSource/listCombobox" textField="text" valueField="id" />
                    </td>
                    <td style="width:100px;">实体名称：</td>
                    <td style="width:150px;">
                         <input name="entityName" class="mini-textbox"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:100px;">是否直接发布：</td>
                    <td style="width:150px;">    
                    	<input type="radio" value="1" name="isPublished">是</input>
                      	<input type="radio" value="0" name="isPublished"  checked="checked">否</input>
                    </td>
                    <td style="width:100px;">&nbsp;</td>
                    <td style="width:150px;">
                    	&nbsp;
                    </td>
                </tr>
            </table>
        </div>
        
        <div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    
    
    <script type="text/javascript">
        mini.parse();

        var form = new mini.Form("form1");

        function SaveData() {
            var o = form.getData();            
			
            form.validate();
            if (form.isValid() == false) return;

            var json = mini.encode(o);
           	
            $.ajax({
                url: "${ctx}/bpmFormTable/save",
				type: 'post',
                data: { data: json },
                cache: false,
                success: function (text) {
                    CloseWindow("save");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
            });
        }

        ////////////////////
        //标准方法接口定义
        function SetData(data) {
            if (data.action == "edit") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                data = mini.clone(data);
                
                $.ajax({
                    url: "${ctx}/bpmFormTable/get/" + data.id,
                    cache: false,
                    success: function (text) {
                        var o = mini.decode(text);
                        form.setData(o);
                        form.setChanged(false);

                    }
                });
            }
        }

        function GetData() {
            var o = form.getData();
            return o;
        }
        
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {
            SaveData();
        }
        
        function onCancel(e) {
            CloseWindow("cancel");
        }
    </script>
</body>
</html>

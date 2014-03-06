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
                    <td style="width:100px;">数据源名称：</td>
                    <td style="width:150px;">    
                        <input name="name" class="mini-textbox" required="true"  emptyText="请输入数据源名称"/>
                    </td>
                    <td style="width:100px;">数据源别名：</td>
                    <td style="width:150px;">
                         <input name="alias" class="mini-textbox" required="true"  emptyText="请输入数据源别名"/>
                    </td>
                </tr>
                <tr>
                    <td>驱动名称：</td>
                    <td>    
                        <input name="driverName" class="mini-textbox" required="true"/>
                    </td>
                    <td >链接地址：</td>
                    <td >    
                        <input name="url" class="mini-textbox" />
                    </td>
                </tr>
               
                <tr>
                    <td >用户名称：</td>
                    <td >  
                        <input name="userName" class="mini-textbox" />
                    </td>
                    <td >密码：</td>
                    <td >    
                        <input name="password" class="mini-password" />
                    </td>
                </tr>
                
                <tr>
                    <td>数据库类型：</td>
                    <td>  
                        <input name="dbType" class="mini-textbox" />
                    </td>
                    <td> <input name="id" class="mini-textbox" style="display:none;"/></td>
                    <td>&nbsp;</td>
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
                url: "${ctx}/sysDataSource/save",
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
                    url: "${ctx}/sysDataSource/get/" + data.id,
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

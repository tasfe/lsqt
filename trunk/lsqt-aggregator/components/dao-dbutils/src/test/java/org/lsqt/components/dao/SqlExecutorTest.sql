drop table procedure_test_table   ;
drop table procedure_test_table2   ;
create table procedure_test_table (
    id int primary key auto_increment,
    stuName varchar(10)
);  

create table procedure_test_table2 (
    id int primary key auto_increment,
    stuName varchar(10),
	stuRemark varchar(20)
);
desc procedure_test_table2 ;
insert into procedure_test_table(stuName) values('zhang');  
insert into procedure_test_table(stuName) values('zeng');  
insert into procedure_test_table(stuName) values('ming'); 


insert into procedure_test_table2  values(1,'zhang','备注1');  
insert into procedure_test_table2  values(2, 'zeng','备注2');  
insert into procedure_test_table2  values(3,'ming','备注3');  
insert into procedure_test_table2  values(4,'zhang','备注4');  

select * from procedure_test_table;  
select * from procedure_test_table2;  

delimiter $
-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 1创建无参查询用的存储过程
drop procedure if exists test_p1 ;
create procedure test_p1 ()
begin
	select * from procedure_test_table;
	select   * from procedure_test_table2 limit 5;
end
$

call test_p1() 
$

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 2创建有参查询用的存储过程
drop procedure if exists test_p2 ;
create procedure test_p2(IN id int)  
begin  
    select * from  procedure_test_table2 t where t.id = id;
	select * from procedure_test_table  limit 5;
end  
$
call test_p2(1) 
$

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 3创建有输入输出参数的存储过程
drop procedure if exists test_p3 ;
create procedure test_p3(IN p_id int, OUT P_stuName varchar(20))  
begin  
  select * from  procedure_test_table  where id = p_id;
   select 'xxxxxx' into P_stuName  ;
end   
$

set @p_stuName='aaaaa';
call test_p3(3，@p_stuName);
-- select @stuName;
$

 

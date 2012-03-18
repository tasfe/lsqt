package com.lsqt.modules.resource.model.oto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/***
 * hibernate中一对一的关联有两种方式：一种是采用外键关联，另外一种是采用主键关联。
 * @author mm
 *
 */
@Entity
public class Husband {
	private int id;
	private String name;
	private Wife wife;

	@Id
	@GeneratedValue // 主键生成器
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn // 这个注解只能写在主(生成ID)的一端
	public Wife getWife() {
		return wife;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWife(Wife wife) {
		this.wife = wife;
	}
}

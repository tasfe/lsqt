package org.lsqt.content.model.otm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TEST_BOOK")
public class TestBook {
	/*
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	*/
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@Column(name = "TITLE")
	private String title;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "STU_ID", referencedColumnName = "id")// 外键为sut_id，与student中的id关联
	private TestStudent student;

	
	public String getId() {
		return id;
	}

	public TestStudent getStudent() {
		return student;
	}

	public void setStudent(TestStudent student) {
		this.student = student;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}

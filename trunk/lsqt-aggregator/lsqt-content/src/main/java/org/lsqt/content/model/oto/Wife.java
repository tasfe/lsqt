package org.lsqt.content.model.oto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Wife {
	private int id;
	private String name;
	private Husband husband;

	@Id
	@GenericGenerator(name = "pkGenerator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "husband") })
	@GeneratedValue(generator = "pkGenerator")
	// wife的ID是根据husband的ID来赋值的，这里需要设置ID生成器的策略为foreign，参数中指定wife的ID是使用husband对象中的ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHusband(Husband husband) {
		this.husband = husband;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "wife")
	public Husband getHusband() {
		return husband;
	}
}

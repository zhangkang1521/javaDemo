package org.zk;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class User {
	@ExcelProperty(value = "入职时间", index = 0, format = "yyyy/MM/dd")
	private Date birthday;

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}

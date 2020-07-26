package org.zk;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class User {

//	@ExcelProperty(value = "工号", index = 0)
//	private String no;

	@ExcelProperty(value = "生日", index = 0, format = "yyyy/MM/dd")
	private Date birthday;

//	@ExcelProperty(value = "生日", index = 5)
//	private String birthdayStr;


//	public String getNo() {
//		return no;
//	}
//
//	public void setNo(String no) {
//		this.no = no;
//	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}

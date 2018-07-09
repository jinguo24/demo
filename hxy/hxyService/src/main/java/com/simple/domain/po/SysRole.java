package com.simple.domain.po;

import com.simple.common.crud.BaseModel;
import javax.persistence.*;
import java.util.*;
import java.math.*;
import javax.persistence.Transient;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;

@Table(name = "sys_role")
public class SysRole extends BaseModel {
	private static final long serialVersionUID = 1L;
	/*角色名称**/
	@io.swagger.annotations.ApiModelProperty(value="角色名称",name="name")
	private String name;
	/*是否可用 0可用 1不可用**/
	@io.swagger.annotations.ApiModelProperty(value="是否可用 0可用 1不可用",name="available")
	private String available;
	/*角色描述**/
	@io.swagger.annotations.ApiModelProperty(value="角色描述",name="description")
	private String description;
	public String  getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}
	public String  getAvailable() {
		return available;
	}
	public void setAvailable(String _available) {
		available = _available;
	}
	public String  getDescription() {
		return description;
	}
	public void setDescription(String _description) {
		description = _description;
	}



	public static enum Field
	{
		Id_ASC("`id` ASC"),Id_DESC("`id` DESC")
			,Name_ASC("`name` ASC"),Name_DESC("`name` DESC")
			,Available_ASC("`available` ASC"),Available_DESC("`available` DESC")
			,Description_ASC("`description` ASC"),Description_DESC("`description` DESC")
	;
		private String value;
		Field(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public void setCol(String value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return this.getValue();
		}
	}
	
	public void setSortColumns(SysRole.Field... fields)
	{
		if (fields == null || fields.length == 0) {
			return;
		}
		for (int k = 0; k < fields.length; k++) {
			if (fields[k] == null) {
				return;
			}
		}
		StringBuilder sb = new StringBuilder(fields[0].toString());
		for (int k = 1; k < fields.length; k++) {
			sb.append(",");
			sb.append(fields[k].toString());
		}
		super.setSortColumns(sb.toString());
	}
	
	@Override
	public void setSortColumns(String sortColumns)
	{
		if (sortColumns == null || "".equals(sortColumns.trim())) {
			return;
		}
		if (sortColumns.contains(",")) {
			String[] cols = sortColumns.split(",");
			java.util.List<Field> fList = new java.util.ArrayList();
			for (int k = 0; k < cols.length; k++) {
				fList.add(Field.valueOf(cols[k]));
			}
			this.setSortColumns(fList.toArray(new Field[fList.size()]));
		} else {
			this.setSortColumns(Field.valueOf(sortColumns));
		}
	}
}

package com.simple.domain.po;

import com.simple.common.crud.BaseModel;
import javax.persistence.*;
import java.util.*;
import java.math.*;
import javax.persistence.Transient;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;

@Table(name = "user_info")
public class UserInfo extends BaseModel {
	private static final long serialVersionUID = 1L;
	/*用户名**/
	@io.swagger.annotations.ApiModelProperty(value="用户名",name="username")
	private String username;
	/*url描述**/
	@io.swagger.annotations.ApiModelProperty(value="登录名",name="name")
	private String name;
	/*密码**/
	@io.swagger.annotations.ApiModelProperty(value="密码",name="password")
	private String password;
	/*创建时间**/
	@io.swagger.annotations.ApiModelProperty(value="创建时间",name="createTime")
	private Date createTime;
	/*最后登录时间**/
	@io.swagger.annotations.ApiModelProperty(value="最后登录时间",name="lastLoginTime")
	private Date lastLoginTime;
	/*1:有效，0:禁止登录**/
	@io.swagger.annotations.ApiModelProperty(value="1:有效，0:禁止登录",name="status")
	private Integer status;
	/*0 不是超管 1是超管**/
	@io.swagger.annotations.ApiModelProperty(value="0 不是超管 1是超管",name="isAdmin")
	private Integer isAdmin;
    //该用户所有的角色
    @Transient
    private List<SysRole> role;
    //该用户包含的所有权限
    @Transient
    private List<SysPermission> permission;
	public String  getUsername() {
		return username;
	}
	public void setUsername(String _username) {
		username = _username;
	}
	public String  getName() {
		return name;
	}
	public void setName(String _name) {
		name = _name;
	}
	public String  getPassword() {
		return password;
	}
	public void setPassword(String _password) {
		password = _password;
	}
	public Date  getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date _createTime) {
		createTime = _createTime;
	}
	public Date  getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date _lastLoginTime) {
		lastLoginTime = _lastLoginTime;
	}
	public Integer  getStatus() {
		return status;
	}
	public void setStatus(Integer _status) {
		status = _status;
	}
	public Integer  getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer _isAdmin) {
		isAdmin = _isAdmin;
	}
    public List<SysRole> getRole() {
        return role;
    }

    public void setRole(List<SysRole> role) {
        this.role = role;
    }

    public List<SysPermission> getPermission() {
        return permission;
    }

    public void setPermission(List<SysPermission> permission) {
        this.permission = permission;
    }



	public static enum Field
	{
		Id_ASC("`id` ASC"),Id_DESC("`id` DESC")
			,Username_ASC("`username` ASC"),Username_DESC("`username` DESC")
			,Name_ASC("`name` ASC"),Name_DESC("`name` DESC")
			,Password_ASC("`password` ASC"),Password_DESC("`password` DESC")
			,CreateTime_ASC("`createTime` ASC"),CreateTime_DESC("`createTime` DESC")
			,LastLoginTime_ASC("`lastLoginTime` ASC"),LastLoginTime_DESC("`lastLoginTime` DESC")
			,Status_ASC("`status` ASC"),Status_DESC("`status` DESC")
			,IsAdmin_ASC("`isAdmin` ASC"),IsAdmin_DESC("`isAdmin` DESC")
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
	
	public void setSortColumns(UserInfo.Field... fields)
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

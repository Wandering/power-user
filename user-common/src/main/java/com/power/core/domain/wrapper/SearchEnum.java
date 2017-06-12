package com.power.core.domain.wrapper;


/**
 * 搜索枚举
 * @author qyang
 *
 */
public enum SearchEnum {
	/** like 全模糊; alk 左匹配 */
	eq("eq", "="), ne("ne", "!="), lt("lt", "<"), le("le", "<="), gt("gt", ">"), ge(
			"ge", ">="),lk("lk","like"),alk("alk","leftlike"),in("in","in");

	private String code;

	private String value;

	private SearchEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */

	public static SearchEnum codeOf(String code) {
		for (SearchEnum searchEnum : SearchEnum.values()) {
			if (searchEnum.getCode().equals(code)) {
				return searchEnum;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static SearchEnum nameOf(String name) {
		for (SearchEnum searchEnum : SearchEnum.values()) {
			if (searchEnum.getCode().equals(name)) {
				return searchEnum;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

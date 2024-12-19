package com.tmn.ata.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum SortDirection {

	ASC("asc"), DESC("desc");

	private String value;

	public static SortDirection fromValue(String value) {
		for (SortDirection dir : SortDirection.values()) {
			if (dir.value.equalsIgnoreCase(value)) {
				return dir;
			}
		}

		throw new IllegalArgumentException("Unknown Sorted Direction" + value);
	}
}

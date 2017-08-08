package com.kazge.sopoexample.common.query;

public enum FilterType {
	LEFTLIKE,
	RIGHTLIKE,
	LIKE,
	EQ,
	NOTEQ,
	LESS,
	GREATER,
	LESSEQ,
	GREATEREQ,
	//only a container for sub condition
	CONTAINER;
}

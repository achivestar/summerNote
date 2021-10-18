package com.pe.bluering.service;

import java.util.List;

import com.pe.bluering.vo.SummerVO;

public interface SummerService {

	void summerInsert(SummerVO summervo);

	int getImgCount();

	int getLastIdx();

	List<SummerVO> getSummerList();

	void summerDelete(int idx);

	SummerVO summerModify(int idx);

	void summerUpdate(SummerVO summervo);

	void summerThumbUpdate(SummerVO summervo);


	
}

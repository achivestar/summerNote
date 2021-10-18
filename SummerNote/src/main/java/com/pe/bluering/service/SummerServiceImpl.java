package com.pe.bluering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.bluering.dao.SummerDAO;
import com.pe.bluering.vo.SummerVO;


@Service
public class SummerServiceImpl implements SummerService{

	
	@Autowired
	SummerDAO dao;

	  
	  public void summerInsert(SummerVO summervo) {
	    this.dao.summerInsert(summervo);
	  }


	@Override
	public int getImgCount() {
		return dao.getImgCount();
	}


	@Override
	public int getLastIdx() {
		return dao.getLastIdx();
	}


	@Override
	public List<SummerVO> getSummerList() {
		return dao.getSummerList();
	}


	@Override
	public void summerDelete(int idx) {
		dao.summerDelete(idx);
	}


	@Override
	public SummerVO summerModify(int idx) {
		return dao.summerModify(idx);
	}


	@Override
	public void summerUpdate(SummerVO summervo) {
		dao.summerUpdate(summervo);
		
	}


	@Override
	public void summerThumbUpdate(SummerVO summervo) {
		dao.summerThumbUpdate(summervo);
	}


}

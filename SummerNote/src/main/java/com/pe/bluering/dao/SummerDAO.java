package com.pe.bluering.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pe.bluering.vo.SummerVO;


@Repository
public class SummerDAO {

	
	@Autowired
	  SqlSessionTemplate sqlSessionTemplate;
	 
	  
	  public void summerInsert(SummerVO summervo) {
	    this.sqlSessionTemplate.insert("summer.summerInsert", summervo);
	  }


	public int getImgCount() {
		return sqlSessionTemplate.selectOne("summer.summerGetImgCount");
	}


	public int getLastIdx() {
		return sqlSessionTemplate.selectOne("summer.summerGetLastIdx");
	}


	public List<SummerVO> getSummerList() {
		return sqlSessionTemplate.selectList("summer.getSummerList");
	}


	public void summerDelete(int idx) {
		 sqlSessionTemplate.delete("summer.summerDelete",idx);
	}


	public SummerVO summerModify(int idx) {
		return sqlSessionTemplate.selectOne("summer.summerModify",idx);
	}


	public void summerUpdate(SummerVO summervo) {
		sqlSessionTemplate.update("summer.summerUpdate",summervo);
	}


	public void summerThumbUpdate(SummerVO summervo) {
		sqlSessionTemplate.update("summer.summerThumbUpdate",summervo);
	}


	  
}

package com.tjoeun.service;

import org.springframework.ui.Model;

import com.tjoeun.vo.MvcBoardVO;

public interface MvcBoardService {
	
	public abstract void execute(MvcBoardVO boardVO);

	public abstract void execute(Model model);
	
}

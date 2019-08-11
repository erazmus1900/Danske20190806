package com.erazmus1900.wordscount;

import java.util.HashMap;
import java.util.List;

import com.erazmus1900.wordscount.business.BusinessConstants;
import com.erazmus1900.wordscount.model.WordsMapperFacadeBean;

public class MainApp {

	public static void main(String args[]) {

		new MainApp().mainCycle();

	}

	public void mainCycle() {

		BusinessConstants bc = new BusinessConstants();
		WordsMapperFacadeBean wmFacade = new WordsMapperFacadeBean();

		List<String> fileNames = wmFacade.readFilesNames(bc.DIRECTORY_FOR_FILES_READING);
		HashMap<String, Integer> wordCountMapper = wmFacade.getGroupWordsByAmount(fileNames);
		wmFacade.getGroupByAlphabet(wordCountMapper);

		System.out.println("Words and amounts saved to files.");

	}

}

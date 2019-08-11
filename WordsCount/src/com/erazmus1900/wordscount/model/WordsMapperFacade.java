package com.erazmus1900.wordscount.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WordsMapperFacade {

	public List<String> readFilesNames(String dirName);

	public HashMap<String, Integer> getGroupWordsByAmount(List<String> fileNames);

	public void getGroupByAlphabet(HashMap<String, Integer> wordCountMapper);

	public void saveWordsToFiles(Map<String, Integer> sortedByKeys, Map<String, Integer> sortedAlphabet,
			Map<String, Integer> mapFileNames);

	public Map<String, Map<String, Integer>> getSortedAlphabetAndFileNames();

	public void appendToFile(String fileName, String content);

	public <K, V> Set<K> getKey(Map<K, V> map, V value);

}

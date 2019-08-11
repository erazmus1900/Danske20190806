package com.erazmus1900.wordscount.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.erazmus1900.wordscount.business.BusinessConstants;

public class WordsMapperFacadeBean implements WordsMapperFacade {

	// read file names;
	public List<String> readFilesNames(String dirName) {

		List<String> fileNames = new ArrayList<String>();
		try {
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirName));
			for (Path path : directoryStream) {
				fileNames.add(path.toString());
			}
		} catch (IOException ex) {
		}

		return fileNames;

	}

	public HashMap<String, Integer> getGroupWordsByAmount(List<String> fileNames) {

		HashMap<String, Integer> wordCountMapper = new HashMap<String, Integer>();

		for (String fileName : fileNames) {

			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

				String line;
				while ((line = br.readLine()) != null) {

					String[] words = line.split("\\s");
					for (String word : words) {

						String cleanedWord = word.toLowerCase().replaceAll("([^a-zA-z])", "");
						if (cleanedWord != "" && cleanedWord.length() > 0) {
							int i = wordCountMapper.get(cleanedWord) != null ? wordCountMapper.get(cleanedWord) : 0;
							wordCountMapper.put(cleanedWord, ++i);
						}

					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return wordCountMapper;

	}

	// save grouped mapped words to separate files;
	public void getGroupByAlphabet(HashMap<String, Integer> wordCountMapper) {

		BusinessConstants bc = new BusinessConstants();
		Map<String, Integer> sortedByKeys = new LinkedHashMap<String, Integer>();

		wordCountMapper.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEachOrdered(x -> sortedByKeys.put(x.getKey(), x.getValue()));

		Map<String, Map<String, Integer>> mapReturned = getSortedAlphabetAndFileNames();
		Map<String, Integer> sortedAlphabet = mapReturned.get(bc.MAP_ALPHABET);
		Map<String, Integer> mapFileNames = mapReturned.get(bc.MAP_FILE_NAMES);

		saveWordsToFiles(sortedByKeys, sortedAlphabet, mapFileNames);

	}

	public void saveWordsToFiles(Map<String, Integer> sortedByKeys, Map<String, Integer> sortedAlphabet,
			Map<String, Integer> mapFileNames) {

		char charForCheck = '0';
		int numberForFile = 0;
		Iterator<Map.Entry<String, Integer>> sortedIterator = sortedByKeys.entrySet().iterator();
		while (sortedIterator.hasNext()) {
			Map.Entry<String, Integer> entry = sortedIterator.next();

			char firstLetter = entry.getKey().charAt(0);
			if (charForCheck != firstLetter) {
				charForCheck = firstLetter;
				numberForFile = sortedAlphabet.get(String.valueOf(charForCheck));

				try {
					Thread.sleep(1000);
					System.out.println("Thread " + Thread.currentThread().getId() + " is running");
				} catch (Exception e) {
					System.out.println("Exception is caught");
				}

			}

			String fileName = getKey(mapFileNames, Integer.valueOf(numberForFile)).iterator().next();
			String content = entry.getKey().toString() + " -> " + entry.getValue().toString() + "\n";

			appendToFile(fileName, content);
		}

	}

	public Map<String, Map<String, Integer>> getSortedAlphabetAndFileNames() {

		BusinessConstants bc = new BusinessConstants();
		Map<String, Integer> sortedAlphabet = new LinkedHashMap<String, Integer>();
		Map<String, Integer> mapFileNames = new LinkedHashMap<String, Integer>();
		int i = 0;

		for (String letters : bc.LETTERS_FOR_DIVIDE) {

			i++;
			char firstLetter = letters.charAt(0);
			char lastLetter = letters.charAt(1);
			String fileNewName = bc.FILE_NEW_NAME_PREFIX + firstLetter + "_" + lastLetter + bc.FILE_NEW_NAME_POSTFIX;

			while (firstLetter <= lastLetter) {
				sortedAlphabet.put(String.valueOf(firstLetter), i);
				mapFileNames.put(fileNewName, i);
				firstLetter++;
			}

		}

		Map<String, Map<String, Integer>> mapReturned = new HashMap<String, Map<String, Integer>>();
		mapReturned.put(bc.MAP_ALPHABET, sortedAlphabet);
		mapReturned.put(bc.MAP_FILE_NAMES, mapFileNames);

		return mapReturned;

	}

	public void appendToFile(String fileName, String content) {

		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(fileName), true);
			os.write(content.getBytes(), 0, content.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public <K, V> Set<K> getKey(Map<K, V> map, V value) {
		Set<K> keys = new HashSet<>();
		for (K key : map.keySet()) {
			if (value.equals(map.get(key))) {
				keys.add(key);
				break;
			}
		}
		return keys;
	}

}

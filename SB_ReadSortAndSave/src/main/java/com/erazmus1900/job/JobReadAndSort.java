package com.erazmus1900.job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobReadAndSort implements Job {

	public String uploadFileName = "wordsToRead.txt";
	public String saveFileName = "savedWords.txt";

	public void execute(JobExecutionContext context) throws JobExecutionException {

		System.out.println("JobReadAndSort --->>> " + uploadFileName);
		List<String> list = readFile(uploadFileName);

		if (list != null) {

			Collections.sort(list);
			System.out.println(list.toString());

			try {
				writeToFileAndSave(list);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public List<String> readFile(String uploadFileName) {

		List<String> list = new ArrayList<String>();

		try {

			Scanner scanner = new Scanner(new File(uploadFileName));
			while (scanner.hasNextLine()) {
				String[] str = scanner.nextLine().split(" ");
				list.addAll(Arrays.asList(str));
				System.out.println(list.toString());
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void writeToFileAndSave(List<String> list) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(saveFileName));

		for (String str : list) {
			writer.write(str);
			writer.newLine();
		}

		writer.close();
	}

}

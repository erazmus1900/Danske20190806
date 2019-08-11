package com.erazmus1900.pyramid.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.erazmus1900.pyramid.business.BusinessConstants;

public class PathCounterFacadeBean implements PathCounterFacade {

	BusinessConstants bc = new BusinessConstants();

	public int getMaxPathAmount() {

		Map<Integer, List<Integer>> intPyramid = readFile();
		Map<Integer, List<Integer>> positionLineValues = getPossiblePathsResults(intPyramid);
		int maxNumber = getMaxNumber(positionLineValues);

		return maxNumber;

	}

	public Map<Integer, List<Integer>> readFile() {

		String fileName = bc.DIRECTORY_FOR_FILES_READING + bc.FILE_NAME;
		Map<Integer, List<Integer>> intPyramid = new HashMap<Integer, List<Integer>>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			int i = 0;
			String line;
			while ((line = br.readLine()) != null) {

				List<Integer> intLine = new ArrayList<Integer>();
				i++;
				String[] strNumbers = line.split(";");
				for (String strNumber : strNumbers) {

					int number = Integer.valueOf(strNumber);
					// System.out.print("->" + number);
					intLine.add(number);

				}
				// System.out.println();

				intPyramid.put(i, intLine);
				// System.out.println("i: " + i + " -> " + intPyramid.keySet());

			}

			br.close();
			System.out.println("intPyramid.size() : " + intPyramid.size());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return intPyramid;

	}

	public void getListOfPossibleSteps(Map<Integer, List<Integer>> intPyramid) {

		int size = intPyramid.size();
		int position = 0;
		int currentLine = 1;
		int firstNumber = 0;
		int secondNumber = 0;

		while (currentLine < size) {

			position = 0;
			System.out.print("currentLine : " + currentLine + " => ");
			List<Integer> listForFirstLine = intPyramid.get(currentLine);
			List<Integer> listForSecondLine = intPyramid.get(currentLine + 1);

			int firstLinePositon = 0;
			for (int checkNumber : listForFirstLine) {
				firstLinePositon++;
				System.out.print("F" + firstLinePositon + " -> ");
				firstNumber = listForSecondLine.get(position);
				if (isPossibleStep(checkNumber, firstNumber)) {
					System.out.print("L" + (currentLine + 1) + "-P" + (position + 1) + " ; ");
				}
				secondNumber = listForSecondLine.get(position + 1);
				if (isPossibleStep(checkNumber, secondNumber)) {
					System.out.print("L" + (currentLine + 1) + "-P" + (position + 2) + " ; ");
				}
				position++;
			}

			currentLine++;
			System.out.println();

		}

	}

	public Map<Integer, List<Integer>> getPossiblePathsResults(Map<Integer, List<Integer>> intPyramid) {

		int size = intPyramid.size();
		Set<Integer> firstLinePositions = new HashSet<Integer>(Arrays.asList(0));
		Map<Integer, List<Integer>> positionLineValues = new HashMap<Integer, List<Integer>>();
		positionLineValues.put(0, intPyramid.get(1));

		/* iteration through each line; */
		for (int currentLine = 1; currentLine < size; currentLine++) {

			System.out.println("---------------------- currentLine : " + currentLine);
			List<Integer> listForFirstLine = intPyramid.get(currentLine);
			List<Integer> listForSecondLine = intPyramid.get(currentLine + 1);

			/*
			 * finding each appropriate position for next line, with described
			 * rules;
			 */
			Map<Integer, List<Integer>> newLinePositionsMap = getPositionsForNextLine(listForFirstLine,
					listForSecondLine, firstLinePositions);
			Set<Integer> positionsMap = newLinePositionsMap.keySet();

			/* temporary map for new line's values savings; */
			Map<Integer, List<Integer>> positionLineValuesTemp = new HashMap<Integer, List<Integer>>();

			/*
			 * each position from previous line calculations for next line
			 * appropriate position;
			 */
			for (int iPosition : positionsMap) {

				List<Integer> nextLinePointPositions = newLinePositionsMap.get(iPosition);

				/* as each point could have more than one "income"; */
				for (int iNextLinePosition : nextLinePointPositions) {

					List<Integer> forAddingCurrent = positionLineValues.get(iPosition);
					List<Integer> forAddingPrevious = positionLineValues.get(iPosition - 1);
					int nextLineVal = listForSecondLine.get(iNextLinePosition);

					List<Integer> newPositionValues = new ArrayList<Integer>();
					if (forAddingCurrent != null) {
						for (int i : forAddingCurrent) {
							newPositionValues.add((i + nextLineVal));
						}
					}
					if (forAddingPrevious != null) {
						for (int j : forAddingPrevious) {
							newPositionValues.add((j + nextLineVal));
						}
					}

					if (newPositionValues != null) {
						positionLineValuesTemp.put(iNextLinePosition, newPositionValues);
					}

				}

			}

			// System.out.println("positionValues.keySet() : " +
			// positionLineValues.keySet());
			// System.out.println("positionValues.values() : " +
			// positionLineValues.values());

			/* clear current map with values and renew with new ones; */
			positionLineValues.clear();
			for (int i : positionLineValuesTemp.keySet()) {
				List<Integer> intTemp = positionLineValuesTemp.get(i);
				positionLineValues.put(i, intTemp);
			}
			// System.out.println("positionValues.keySet() repeat : " +
			// positionLineValues.keySet());
			// System.out.println("positionValues.values() repeat : " +
			// positionLineValues.values());

			/*
			 * for the next iteration we should mark, which line's positions we
			 * should check and recalculate;
			 */
			firstLinePositions = positionLineValues.keySet();

		}

		return positionLineValues;

	}

	public int getMaxNumber(Map<Integer, List<Integer>> positionLineValues) {

		int maxNumber = 0;

		if (positionLineValues != null) {
			for (List<Integer> intList : positionLineValues.values()) {
				for (int iVal : intList) {
					if (iVal > maxNumber) {
						maxNumber = iVal;
					}
				}
			}

			System.out.println("maxNumber: " + maxNumber);
			return maxNumber;

		} else {
			return 0;
		}

	}

	public Map<Integer, List<Integer>> getPositionsForNextLine(List<Integer> listForFirstLine,
			List<Integer> listForSecondLine, Set<Integer> firstLinePositions) {

		Map<Integer, List<Integer>> newLinePositionsMap = new HashMap<Integer, List<Integer>>();

		for (int position : firstLinePositions) {

			List<Integer> positionList = new ArrayList<Integer>();
			int checkNumber = listForFirstLine.get(position);
			int firstNumber = listForSecondLine.get(position);
			int secondNumber = listForSecondLine.get(position + 1);

			if (isPossibleStep(checkNumber, firstNumber)) {
				positionList.add(position);
			}
			if (isPossibleStep(checkNumber, secondNumber)) {
				positionList.add(position + 1);
			}
			newLinePositionsMap.put(position, positionList);

		}
		// System.out.println("newLinePositionsMap.keySet() : " +
		// newLinePositionsMap.keySet());
		// System.out.println("newLinePositionsMap.values() : " +
		// newLinePositionsMap.values());

		return newLinePositionsMap;

	}

	public boolean isPossibleStep(int checkNumber, int secondLineNumber) {

		boolean evenCheck = checkForEven(checkNumber);
		boolean evenSecondLine = checkForEven(secondLineNumber);

		if (evenCheck) {
			if (!evenSecondLine) {
				return true;
			} else {
				return false;
			}
		} else {
			if (evenSecondLine) {
				return true;
			} else {
				return false;
			}
		}

	}

	public Boolean checkForEven(int int1) {

		return int1 % 2 == 0 ? true : false;

	}

}

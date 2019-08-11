package com.erazmus1900.pyramid.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PathCounterFacade {

	public int getMaxPathAmount();

	public Map<Integer, List<Integer>> readFile();

	public void getListOfPossibleSteps(Map<Integer, List<Integer>> intPyramid);

	public Map<Integer, List<Integer>> getPossiblePathsResults(Map<Integer, List<Integer>> intPyramid);

	public int getMaxNumber(Map<Integer, List<Integer>> positionLineValues);

	public Map<Integer, List<Integer>> getPositionsForNextLine(List<Integer> listForFirstLine,
			List<Integer> listForSecondLine, Set<Integer> firstLinePositions);

	public boolean isPossibleStep(int checkNumber, int secondLineNumber);

	public Boolean checkForEven(int int1);

}

package com.capgemini.iplanalysernew;
/**
 * 
 *
 */
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import com.capgemini.csvbuilder.BuilderException;
import com.capgemini.csvbuilder.CsvBuilderFactory;
import com.capgemini.csvbuilder.ICsvBuilder;

public class IPLanalyser {
	
	List<PlayerRuns> playerRunsList = null;
	private Comparator<PlayerRuns> censusComparator;
	Comparator<PlayerRuns> runsComparator = null;

	/**
	 * @param filePath
	 * @throws IPLAnalyserException
	 */
	public void loadRunsData(String filePath) throws IPLAnalyserException{
		try {
			Reader reader = Files.newBufferedReader(Paths.get(filePath));
			new CsvBuilderFactory();
			ICsvBuilder csvBuilderCustom = CsvBuilderFactory.createBuilderCommons();

			playerRunsList = csvBuilderCustom.getCSVFileList(reader,PlayerRuns.class);	
           
		} catch (IOException e) {
			throw new IPLAnalyserException(IPLAnalyserException.Exception.INCORRECT_FILE);
		}

	}

	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getTopBattingAvg() throws IPLAnalyserException {
		if (playerRunsList == null || playerRunsList.size() == 0) {
            throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
        }
		double max = playerRunsList.stream().filter(s->!s.average.equals("-")).map(s->Double.parseDouble(s.average)).max(Double::compare).get();
		List<PlayerRuns> player = playerRunsList.stream().filter(s->s.average.equals(Double.toString(max))).collect(Collectors.toList());
		return player.get(0).player;		
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getTopStrikeRate() throws IPLAnalyserException {
		if (playerRunsList == null || playerRunsList.size() == 0) {
			throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
		}
		double maxStrikeRate = playerRunsList.stream().map(s -> s.strikeRate).max(Double::compare).get();
		List<PlayerRuns> player = playerRunsList.stream().filter(s -> s.strikeRate == maxStrikeRate)
				.collect(Collectors.toList());
		return player.get(0).player;
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getMaximum6sAnd4s() throws IPLAnalyserException {
		if (playerRunsList == null || playerRunsList.size() == 0) {
			throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
		}
		int maxSixesAndFours = playerRunsList.stream().map(s -> s.sixes + s.fours).max(Integer::compare).get();
		List<PlayerRuns> player = playerRunsList.stream().filter(s -> s.sixes + s.fours == maxSixesAndFours)
				.collect(Collectors.toList());
		return player.get(0).player;
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getBestStrickRateMaximum6sAnd4s() throws IPLAnalyserException {
		checkForData();
		censusComparator = Comparator.comparing(s -> s.sixes + s.fours);
		censusComparator = censusComparator.thenComparing(s -> s.strikeRate);
		this.sortBatsmenData(censusComparator);
		Collections.reverse(playerRunsList);
		return playerRunsList.get(0).player;
	}

	public void checkForData() throws IPLAnalyserException {
		if (playerRunsList == null || playerRunsList.size() == 0) {
			throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
		}
	}
	public String getGreatAvgwithBestStrickRate() throws IPLAnalyserException {
		checkForData();
		Comparator<PlayerRuns> runsComparator = Comparator.comparing(PlayerRuns::getAverage).thenComparing(s -> s.strikeRate);
		return getBatsmanName();
	}
	public String getMaxRunsWithBestAvg() throws IPLAnalyserException {
		checkForData();
		runsComparator = Comparator.comparing(s -> s.runs);
		runsComparator = runsComparator.thenComparing(PlayerRuns::getAverage);
		return getBatsmanName();
	}

	private String getBatsmanName() {
		Comparator<PlayerRuns> runsComparator = null;
		this.sortBatsmenData(runsComparator);
		Collections.reverse(playerRunsList);
		return playerRunsList.get(0).player;
	}

	/**
	 * @param censusComparator2
	 */
	private void sortBatsmenData(Comparator<PlayerRuns> censusComparator2) {
		for (int i = 0; i < playerRunsList.size() - 1; i++) {
			for (int j = 0; j < playerRunsList.size() - i - 1; j++) {
				PlayerRuns census1 = playerRunsList.get(j);
				PlayerRuns census2 = playerRunsList.get(j + 1);
				if (censusComparator2.compare(census1, census2) > 0) {
					playerRunsList.set(j, census2);
					playerRunsList.set(j + 1, census1);
				}
			}
		}

}
}
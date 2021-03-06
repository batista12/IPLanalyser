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
	private List<IPLBowling> bowlerDataList = null;
	private Comparator<IPLBowling> bowlerComparator;

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
	public void loadWktsData(String filePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {
			new CsvBuilderFactory();
			ICsvBuilder csvBuilderCustom = CsvBuilderFactory.createBuilderCommons();
			bowlerDataList = csvBuilderCustom.getCSVFileList(reader, IPLBowling.class);

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
		checkForData1();
		censusComparator = Comparator.comparing(s -> s.sixes + s.fours);
		censusComparator = censusComparator.thenComparing(s -> s.strikeRate);
		this.sortBatsmenData(censusComparator);
		Collections.reverse(playerRunsList);
		return playerRunsList.get(0).player;
	}

	public void checkForData1() throws IPLAnalyserException {
		if (playerRunsList == null || playerRunsList.size() == 0) {
			throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
		}
	}
	public String getGreatAvgwithBestStrickRate() throws IPLAnalyserException {
		checkForData1();
		Comparator<PlayerRuns> runsComparator = Comparator.comparing(PlayerRuns::getAverage).thenComparing(s -> s.strikeRate);
		return getBatsmanName();
	}
	public String getMaxRunsWithBestAvg() throws IPLAnalyserException {
		checkForData1();
		runsComparator = Comparator.comparing(s -> s.runs);
		runsComparator = runsComparator.thenComparing(PlayerRuns::getAverage);
		return getBatsmanName();
	}
	public String getTopBowlingAvg() throws IPLAnalyserException {
		checkForBowlerData();
		bowlerComparator = Comparator.comparing(IPLBowling::getAverage);
		return getBowlerName();
	}
	private String getBowlerName() {
		this.sortBowlerData(bowlerComparator);
		return bowlerDataList.get(0).player;
	}

	private void sortBowlerData(Comparator<IPLBowling> bowlerComparator2) {
		
	}
	public String getTopBowlingStrakeRate() throws IPLAnalyserException {
		checkForBowlerData();
		bowlerComparator = Comparator.comparing(IPLBowling::getStrikeRate);
		return getBowlerName();
	}

	/**
	 * @throws IPLAnalyserException
	 */
	private void checkForBowlerData() throws IPLAnalyserException {
		List<PlayerRuns> bowlerDataList = null;
		if (bowlerDataList == null || bowlerDataList.size() == 0) {
			throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
		}
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getBestEconomy() throws IPLAnalyserException {
		checkForBowlerData();
		bowlerComparator = Comparator.comparing(s -> s.economy);
		return getBowlerName();
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getBestStrikeRateWith4w5w() throws IPLAnalyserException {
		checkForBowlerData();
		bowlerComparator = Comparator.comparing(s -> s.fourWickets + s.fiveWickets);
		bowlerComparator = bowlerComparator.reversed();
		bowlerComparator = bowlerComparator.thenComparing(IPLBowling::getStrikeRate);
		return getBowlerName();
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getGreatAvgWithBestStrikeRate() throws IPLAnalyserException {
		checkForBowlerData();
		bowlerComparator = Comparator.comparing(IPLBowling::getAverage);
		bowlerComparator = bowlerComparator.thenComparing(IPLBowling::getStrikeRate);
		return getBowlerName();
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getMaxWktsWithBestAvg() throws IPLAnalyserException {
		checkForBowlerData();
		bowlerComparator = Comparator.comparing(s -> s.fourWickets + s.fiveWickets);
		bowlerComparator = bowlerComparator.reversed();
		bowlerComparator = bowlerComparator.thenComparing(IPLBowling::getAverage);
		return getBowlerName();
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getBestBattingAvgBestBowlingAvg() throws IPLAnalyserException {
		checkForBowlerData();
		runsComparator = Comparator.comparing(PlayerRuns::getAverage);
		getBatsmanName();
		bowlerComparator = Comparator.comparing(IPLBowling::getAverage);
		getBowlerName();

		for (PlayerRuns b : playerRunsList) {
			for (IPLBowling bo : bowlerDataList) {
				if (b.player.equals(bo.player)) {
					return b.player;
				}
			}
		}
		return null;
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getBestAllRounder() throws IPLAnalyserException {
		checkForBowlerData();
		runsComparator = Comparator.comparing(s -> s.runs);
		getBatsmanName();
		bowlerComparator = Comparator.comparing(s -> s.wickets);
		bowlerComparator = bowlerComparator.reversed();
		getBowlerName();

		for (PlayerRuns b : playerRunsList) {
			for (IPLBowling bo : bowlerDataList) {
				if (b.player.equals(bo.player)) {
				
					return b.player;
				}
			}
		}
		return null;
	}
	/**
	 * @return
	 * @throws IPLAnalyserException
	 */
	public String getZeroHundredsFiftiesWithBestAvg() throws IPLAnalyserException {
		checkForData();
		runsComparator = Comparator.comparing(s -> s.hundreds + s.fifties);
		runsComparator = runsComparator.reversed();
		runsComparator = runsComparator.thenComparing(PlayerRuns::getAverage);
		return getBatsmanName();
	}
	/**
	 * @return
	 */
	private String getBatsmanName() {
		Comparator<PlayerRuns> runsComparator = null;
		this.sortBatsmenData(runsComparator);
		Collections.reverse(playerRunsList);
		return playerRunsList.get(0).player;
	}
	/**
	 * @throws IPLAnalyserException
	 */
	private void checkForData() throws IPLAnalyserException {
		if (playerRunsList == null || playerRunsList.size() == 0) {
			throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
		}
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
		private void sortBowlerData1(Comparator<IPLBowling> comparator) {
			for (int i = 0; i < bowlerDataList.size() - 1; i++) {
				for (int j = 0; j < bowlerDataList.size() - i - 1; j++) {
					IPLBowling census1 = bowlerDataList.get(j);
					IPLBowling census2 = bowlerDataList.get(j + 1);
					if (comparator.compare(census1, census2) > 0) {
						bowlerDataList.set(j, census2);
						bowlerDataList.set(j + 1, census1);
					}
				}
			}
}

}
package com.capgemini.iplanalysernew;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
public class IPLanalyserTest {
	private final String PLAYER_RUNS_DATA = "C:\\Users\\ASUS\\eclipse-workspace\\IPLanalyserNew\\src\\main\\resources\\batsmanruns.csv";
	IPLanalyser iplAnalyser = null;
	@Before
	public void createIplAnalyserObject() {
		iplAnalyser = new IPLanalyser();
	}

	@Test
	public void givenCsvDataShouldReturnTopBattingAvg() {
		try {
			iplAnalyser.loadRunsData(PLAYER_RUNS_DATA);
			String playerName = iplAnalyser.getTopBattingAvg();
			assertEquals("MS Dhoni", playerName);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void givenCsvDataShouldReturnTopStrikeRatePlayer() {
		try {
			iplAnalyser.loadRunsData(PLAYER_RUNS_DATA);
			String playerName = iplAnalyser.getTopStrikeRate();
			assertEquals("Ishant Sharma", playerName);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void givenCsvDataShouldReturnPlayerwithMax6sand4s() {
		try {
			iplAnalyser.loadRunsData(PLAYER_RUNS_DATA);
			String playerName = iplAnalyser.getMaximum6sAnd4s();
			assertEquals("Andre Russell", playerName);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}
		@Test
		public void givenCsvDataShouldReturnPlayerwithBestStrikeRateWithMax6sand4s() {
			try {
				iplAnalyser.loadRunsData(PLAYER_RUNS_DATA);
				String playerName = iplAnalyser.getBestStrickRateMaximum6sAnd4s();
				assertEquals("Andre Russell", playerName);
			} catch (IPLAnalyserException e) {
				e.printStackTrace();
			}
		}
			@Test
			public void givenCsvDataShouldReturnPlayewithGreatAvgrwithBestStrikeRate() {
				try {
					iplAnalyser.loadRunsData(PLAYER_RUNS_DATA);
					String playerName = iplAnalyser.getGreatAvgwithBestStrickRate();
					assertEquals("MS Dhoni", playerName);
				} catch (IPLAnalyserException e) {
					e.printStackTrace();
				}
			}
			@Test
			public void givenCsvDataShouldReturnPlayewithMaxRunsrWithBestAvg() {
				try {
					iplAnalyser.loadRunsData(PLAYER_RUNS_DATA);
					String playerName = iplAnalyser.getMaxRunsWithBestAvg();
					assertEquals("David Warner", playerName);
				} catch (IPLAnalyserException e) {
					e.printStackTrace();
				}
			}
	}
		


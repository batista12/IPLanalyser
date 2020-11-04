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

}

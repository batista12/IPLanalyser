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

	public String getTopBattingAvg() throws IPLAnalyserException {
		if (playerRunsList == null || playerRunsList.size() == 0) {
            throw new IPLAnalyserException(IPLAnalyserException.Exception.NO_CENSUS_DATA);
        }
		double max = playerRunsList.stream().filter(s->!s.average.equals("-")).map(s->Double.parseDouble(s.average)).max(Double::compare).get();
		List<PlayerRuns> player = playerRunsList.stream().filter(s->s.average.equals(Double.toString(max))).collect(Collectors.toList());
		return player.get(0).player;		
	}
	

}

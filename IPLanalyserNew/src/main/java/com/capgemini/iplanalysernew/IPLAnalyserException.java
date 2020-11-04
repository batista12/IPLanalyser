package com.capgemini.iplanalysernew;

public class IPLAnalyserException extends Exception {


	enum Exception {
		UNABLE_TO_PARSE, INCORRECT_FILE, INCORRECT_FILE_TYPE, INCORRECT_DELIMITER, INCORRECT_HEADER, NO_CENSUS_DATA
	}

	Exception exception;

	public IPLAnalyserException(Exception exception) {
		this.exception = exception;
	}

}

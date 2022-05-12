package myfitnessdiary.persistence;

import java.io.Writer;
import java.time.LocalDate;

import myfitnessdiary.model.FitnessDiary;

public interface ReportWriter {
	void printReport(Writer writer, LocalDate date, FitnessDiary diary);
}

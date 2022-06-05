package rfd.persistence;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import rfd.model.RailwayLine;

public interface RailwayLineReader {

	public static Optional<List<String>> getAllLineNames(Path path){ 
		File f = path.toFile();
		if (f.isDirectory()) { 
			String[] someFilenames = f.list( (dir, name) -> name.endsWith(".txt") );
			return Optional.of(Arrays.asList(someFilenames));
		}
		else return Optional.empty();
	}

	public RailwayLine getRailwayLine(Reader rdr) throws IOException;
}
package bussy.persistence;
import java.io.*;
import java.util.Map;

import bussy.model.Linea;

public interface TransportLinesReader {
	public Map<String,Linea> readTransportLines(BufferedReader reader) throws IOException, BadFileFormatException;
	public static TransportLinesReader of() {return new MyTransportLinesReader();}
}

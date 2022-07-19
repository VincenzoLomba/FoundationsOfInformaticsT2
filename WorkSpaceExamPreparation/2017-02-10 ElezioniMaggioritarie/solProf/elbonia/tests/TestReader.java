package elbonia.tests;
import elbonia.model.Collegio;
import elbonia.persistence.BadFileFormatException;
import elbonia.persistence.CollegiReader;
import elbonia.persistence.MyCollegiReader;

import java.io.*;
import java.util.List;

public class TestReader {
	public static void main(String[] args) {
		try	{
			FileReader reader = new FileReader("RisultatiGallia.txt");
			CollegiReader myReader = new MyCollegiReader();
			List<Collegio> listaCollegi = myReader.caricaElementi(reader);	
			System.out.println(listaCollegi);
			reader.close();
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (BadFileFormatException e) {
			e.printStackTrace();
		}
	}
}

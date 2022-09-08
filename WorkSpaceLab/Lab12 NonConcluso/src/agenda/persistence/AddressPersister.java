package agenda.persistence;

import java.util.StringTokenizer;

import agenda.model.Address;
import agenda.model.Detail;

public class AddressPersister implements DetailPersister {

	/* Address;Job;Viale del Risorgimento;2;40100;Bologna;Italia */
	
	private static final String SEPARATOR = ";";
	
	@Override
	public Detail load(StringTokenizer source) throws BadFileFormatException {
		
		if (source.countTokens() != 6) throw new BadFileFormatException("Una riga descrittiva di un \"Address\" e' formatta in maniera errata.");
		Address address = new Address();
		address.setDescription(source.nextToken());
		address.setStreet(source.nextToken());
		address.setNumber(source.nextToken());
		address.setZipCode(source.nextToken());
		address.setCity(source.nextToken());
		address.setState(source.nextToken());
		return address;
	}

	@Override
	public void save(Detail d, StringBuilder sb) {
		
		if (d instanceof Address a) {
			sb.append(a.getDescription()).append(SEPARATOR)
				.append(a.getStreet()).append(SEPARATOR)
				.append(a.getNumber()).append(SEPARATOR)
				.append(a.getZipCode()).append(SEPARATOR)
				.append(a.getCity()).append(SEPARATOR)
				.append(a.getState()).append("\n")
			;
		} else {
			throw new IllegalArgumentException(
				"Non e' possibile effettuare persistenza di un oggetto di classe non " + Address.class.getSimpleName() + " utilizzando un " + AddressPersister.class.getSimpleName()
			);
		}
	}

}

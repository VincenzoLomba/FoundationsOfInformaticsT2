package dentinia.governor.persistence;

import java.io.IOException;

import dentinia.governor.model.Elezioni;

public interface SeggiWriter {
	void stampaSeggi(Elezioni elezioni, String msg) throws IOException;
}

package media.filters;

import lombok.Setter;
import media.Media;
import media.Type;

@Setter
public class TypeFilter implements Filter {
	
	private Type typeToFind = null;
	
	public TypeFilter (Type typeToFind) { this.typeToFind = typeToFind;	}

	@Override
	public boolean filter(Media m) { return m.getType().equals(this.typeToFind); }

}

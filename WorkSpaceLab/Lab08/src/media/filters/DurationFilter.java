package media.filters;

import lombok.Setter;
import media.Media;

@Setter
public class DurationFilter implements Filter {
	
	private int duration = -1;
	
	public DurationFilter (int duration) { this.duration = duration; }
	
	@Override
	public boolean filter (Media m) {
		
		if (m instanceof HasDuration hd)
			return this.duration == 0 || hd.getDuration() <= this.duration;
		return false;
	}

}

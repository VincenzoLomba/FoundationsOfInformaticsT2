package rfd.model;

public abstract class PointOfInterest {
	
	private String stationName;
	private String progressivaKm;

	public PointOfInterest(String name, String progressivaKm) {
		this.stationName = name;
		this.progressivaKm = progressivaKm;
	}

	public String getStationName() { return stationName; }
	public String getKm() { return progressivaKm; }
	abstract public double getKmAsNum();

	@Override
	public String toString() {
		return getKm() + "\t" + getStationName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((progressivaKm == null) ? 0 : progressivaKm.hashCode());
		result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PointOfInterest other = (PointOfInterest) obj;
		if (progressivaKm == null) {
			if (other.progressivaKm != null) return false;
		} else if (!progressivaKm.equals(other.progressivaKm)) return false;
		if (stationName == null) {
			if (other.stationName != null) return false;
		} else if (!stationName.equals(other.stationName)) return false;
		return true;
	}

}

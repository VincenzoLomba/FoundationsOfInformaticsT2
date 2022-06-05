package rfd.model;

import java.text.NumberFormat;

public class Segment {
	
	private PointOfInterest from, to;
	private NumberFormat formatter;

	public Segment(PointOfInterest from, PointOfInterest to) {
		
		super();
		this.from = from;
		this.to = to;
		formatter = NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(2);
	}

	@Override
	public String toString() {
		/* return "Da " + from.getStationName() + " a " + to.getStationName() +", km " + formatter.format(this.getLength()); */
		return String.format("%-45s%-4s%8s", "Da " + from.getStationName() + " a " + to.getStationName(), "km", formatter.format(this.getLength()));
	}

	public PointOfInterest getFrom() { return from; }
	public PointOfInterest getTo() { return to; }
	public double getLength(){ return Math.abs(to.getKmAsNum()-from.getKmAsNum()); }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}

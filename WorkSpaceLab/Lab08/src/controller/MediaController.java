package controller;

import media.Media;
import media.collection.MediaCollection;
import media.filters.Filter;

public class MediaController {
	
	private MediaCollection allMedias = null;
	
	public MediaController () {
		
		this.allMedias = new MediaCollection();
	}

	public boolean add (Media m) {
		
		for (int j = 0 ; j < allMedias.size() ; ++j)
			if (m.equals(allMedias.get(j))) return false;
		allMedias.add(m);
		return true;
	}
	
	public MediaCollection getAll () {
		
		MediaCollection response = new MediaCollection(allMedias.size());
		for (int j = 0 ; j < allMedias.size() ; ++j)
			response.add(allMedias.get(j));
		return response;
	}
	
	public boolean remove (Media m) {
		
		for (int j = 0 ; j < allMedias.size() ; ++j)
			if (allMedias.get(j).equals(m)) {
				allMedias.remove(j);
				return true;
			}
		return false;
	}
	
	public MediaCollection find (Filter f) {
		
		MediaCollection response = new MediaCollection();
		for (int j = 0 ; j < allMedias.size() ; ++j)
			if (f.filter(allMedias.get(j))) response.add(allMedias.get(j));
		return response;
	}
}

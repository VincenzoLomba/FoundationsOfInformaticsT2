package agenda.model;

public  class DetailFactory {
	
	private static String[] names = {"Email", "Phone","Address"};
	
	public static String[] getNames() { return names; }
	
	public static Detail of(String name) {	
		
		switch(name){
		
			case "Email" : return new EMail();
			case "Phone": return new Phone();
			case "Address": return new Address() ;
			default: return  null;
		}
	}
}

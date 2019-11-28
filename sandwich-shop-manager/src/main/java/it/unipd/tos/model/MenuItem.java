////////////////////////////////////////////////////////////////////
// [Andrea] [Polo-Perucchin] [1169765]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class MenuItem {
	itemType type;
	String name;
	double price;
	
	public enum itemType{
		PANINO,
		FRITTO,
		BEVANDA
	}
	public MenuItem(itemType type, String name, double price){
		this.type = type;
		this.name = name;
		this.price = price;
	}
	
	public String toString(){
		return "Tipo: "+this.type + "Nome: "+this.name + "Prezzo: "+ this.price;
	}
	
	public double getPrice(){
		return this.price;
		
	}
	
}

package cz.vutbr.feec.bmds;

import java.util.ArrayList;

/**
 * Třída MyClass, která implementuje rozhraní ISum.
 * Třída obsahuje pole prvků Integer a zveřejnuje metody umožnující práci s tímto polem.
 */
public class MyClass implements ISum {
	
	
	private static int count = 0;	// statická proměnná, ve které je uchováno, kolikrát již byl vytvořen objekt typu MyClass celkem vytvořena
	private ArrayList<Integer> list = new ArrayList<Integer>(); // samotné pole, které je zvenku skryto
	
	// KONSTUKTORY // 
	
	/**
	 * Konstruktor metody. Po jeho zavolání dojde k navýšení počítadla počtu vytvořených objektů.
	 */
	public MyClass() {
		count++;
	}
	
	/**
	 * Druhý konstruktor metody, který umožnuje automaticky vytvořit třídu, ve které jsou již předplněny některé prvky.
	 * Jde o nekončený seznam parametrů (args), použije se např. new MyClass(1,2,3,4);
	 * @throws Exception 
	 */
	public MyClass(int...numbers) throws IllegalArgumentException {
		this(); // tímto volání se zavolá konstruktor metody MyClass() - bez parametru. 
		
		for (int i : numbers) {
			addInteger(i);
		}
		
		
	}
	
	// STATICKE METODY //
	
	/**
	 * Statická metoda! Vrací počet již vytvořených objektů třídy MyClass.
	 * Lze volat například MyClass.getCount()!
	 * @return
	 */
	public static int getCount() {
		return count;
	}
	
	/**
	 * Staticka metoda! Na základě dvou objektů třídy MyClass vytvoří nový objekt, který bude obsahovat prvky z obou objektů v parametru.
	 * Pouziti napr. MyClass united = MyClass.createUnited(prvniObjekt, druhyObjekt); 
	 * @return MyClass
	 */
	public static MyClass createUnited(MyClass prvniObjekt, MyClass druhyObjekt) {
		MyClass newClass = new MyClass(); // Vytvoření prázdného objektu
		
		// Díky tomu, že pracujeme stále v MyClass, list v objektech (které jsou jinak private), jsou viditelné a lze s nimi pracovat.
		// Vně MyClass by podobný přístup nefungoval, pokud bude list private.
		newClass.list.addAll(prvniObjekt.list); // Vložení prvků z pole prvního objektu
		newClass.list.addAll(druhyObjekt.list); // Vložení prvků z pole druhého objektu
				
		return newClass;
		
		// POZOR! Objekt nejde zkopírovat takto MyClass newClass = prvni; Předala by se pouze reference na první objekt!!
		
	}
	
	// TRÍDNÍ METODY //
	
	/**
	 * Vlozi cislo do seznamu. V prípadě, že je menší než nula, je vyhozena výjimka IllegalArgumentException.
	 * @param i	Vkladane cislo
	 * @throws IllegalArgumentException	Vyhodi v pripade, ze je vlozeno zaporne cislo.
	 */
	public void addInteger(int i) throws IllegalArgumentException {
		if(i > 0) {
			list.add(i);
		}
		else {
			// Seznam vestavěných výjimek v javě: https://www.tutorialspoint.com/java/java_builtin_exceptions.htm
			throw new IllegalArgumentException("Nelze vkladat zaporna cisla.");
		}
		
	}	
	
	/**
	 * Zjistí, zda parametr i existuje v seznamu
	 * @param i
	 * @return Vraci zda existuje.
	 */
	public boolean integerExists(int i) {
		return list.contains(i);
	}
	
	/**
	 * Implementuje metodu toString. Při pokusu o převedení objektu na řetězec (napr. při System.out.println) je vrácen výstup této funkce.
	 * Jde o překrytí funkce toString() definované v pole Object, ze kterého dědí všechny třídy v Javě.
	 * @return String 
	 */
	public String toString() {
		return "POLE o velikosti " + list.size() + " se souctem " + this.sum();
	}

	/**
	 * Vrátí součet všech prvků v listu. @Overide znamená, že implementuje funkci rozhraní ISum.
	 * @return Součet všech prvků.
	 */
	@Override
	public int sum() {
		
		int sum = 0;
		for (Integer i : list) {
			sum += i;
		}
		return sum;
		
	}
	
	/**
	 * Vypíše obsah pole na System.out.
	 * Pole(počet prvků): X X X
	 */
	public void print() {
		System.out.print("Pole(" + list.size() + "): ");
		for (Integer integer : list) {
			System.out.print(integer + " ");
		}
		System.out.println();
	}
	
	

}

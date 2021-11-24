import cz.vutbr.feec.bmds.MyClass; // Je naimportovan balicek, protoze trida Spustitelna je ve vychozim balicku.

public class Spustitelna {

    public static void main(String[] args) {

        // Jsou vytvořeny dvě třídy MyClass
        MyClass prvni = new MyClass();
        MyClass druha = new MyClass();
        MyClass treti = null;

        // V bloku try je spuštěn kód, který může vyvolat výjimku IllegalArgumentException

        try {

            // Je vytvořena další třída, již pomocí konstruktoru umožnující přímé vložení prvků.
            // Toto již může vyvolat výjimku a musí být provedeno v bloku try

            treti = new MyClass(1,2,3,4,5); // Zde byla "treti" inicializovana az v bloku try.
            // Diky tomu je viditelna v celé metodě main.

            // Přidáme nějaká čísla do polí
            prvni.addInteger(20);
            prvni.addInteger(20);
            prvni.addInteger(40);

            druha.addInteger(68);
            druha.addInteger(1);

            // Výpis
            prvni.print();
            druha.print();

        } catch (IllegalArgumentException e) {
            // V případě, že je detekována výjimka, je vypsána chybová hláška na Chybový výstup(STDERR), ukáže se červeně.
            System.err.println("Chyba: " + e.getMessage());
        }

        // Zobrazení počtu vytvořených tříd
        System.out.println("Počet vytvořených tříd: " + MyClass.getCount());

        System.out.println(prvni.toString());

        // Test zda ve třetí třídě existuje číslo 4
        System.out.println("Existuje 4ka: " + treti.integerExists(4));

        // Soucet vsech prvků pole
        System.out.println("Součet prvků prvního pole: " +  prvni.sum()); // 80 - dvacítka je skutečně vložena dvakrát (vlastnost ArrayListu)

        // Ukázka vytvoření sjednoceného objektu pomocí statické metody (továrna)
        MyClass united = MyClass.createUnited(prvni, druha);
        united.print(); // vypis sjednoceneho objektu

        // Ukázka použití toString. K objektu se chovám jako by to byl string
        System.out.println(united);
        String unitedInfo = united.toString(); // případně lze i uložit jako jiný String


    }

}

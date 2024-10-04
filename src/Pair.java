package hw03;

public class Pair<E>  {

    public E Val1;
    public E Val2;

    public Pair(E Val1, E Val2) {
        this.Val1 = Val1;
        this.Val2 = Val2;

    }

    public E getFirst(){
        return Val1;
    }

    public E getSecond() {
        return Val2;
    }

    public void setFirst(E newVal) {
        Val1 = newVal;
    }

    public void setSecond(E newVal) {
        Val2 = newVal;
    }


    public static void main(String[] args) {
        Pair intPair = new Pair(1,2);
        System.out.println(intPair.Val1 + " " + intPair.Val2);

        Pair mixPair = new Pair(1.0, "cat");
        System.out.println(mixPair.Val1 + " " + mixPair.Val2);

        Pair pairPair = new Pair(intPair, mixPair);
        System.out.println(pairPair.Val1 + " " + pairPair.Val2);
    }



    
}


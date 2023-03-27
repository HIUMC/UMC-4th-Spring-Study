class Cal{
    public int sum(int v1, int v2){
        return v1+v2;
    }
//
//    //Overloading
//    public int sum(int v1, int v2, int v3){
//        return this.sum(v1, v2)+v3;
//    }
    int v1, v2;
    Cal(int v1, int v2){
        System.out.println("Cal init!");
        this.v1= v1; this.v2= v2;
    }
}

class Cal3 extends Cal{

    Cal3(int v1, int v2) {
        super(v1, v2);
        System.out.println("Cal3 init!");
    }
    public int minus(int v1, int v2){
        return v1-v2;
    }
//
//    //Overriding : 재정의
//    public int sum(int v1, int v2){
//        System.out.println("Cal3!");
//        return super.sum(v1, v2);
//    }

}

public class InheritanceApp {
    public static void main(String[] args) {
        Cal c = new Cal(2, 1);
        Cal3 c3 = new Cal3(2, 1);

    }
}

class Cal2{
    public int sum(int v1, int v2){
        return v1+v2;
    }

    public int minus(int v1, int v2){
        return v1-v2;
    }
}
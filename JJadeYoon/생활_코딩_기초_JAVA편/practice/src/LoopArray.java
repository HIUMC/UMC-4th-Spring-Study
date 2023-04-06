public class LoopArray {
    public static void main(String[] args) {
        String[] users = new String[3];
        users[0]="egoing";
        users[1]="jinhyuk";
        users[2]="youbin";

        /*
         <li>egoing</li>
         <li>jinhyuk</li>
         <li>youbin</li>
         */

        for(int i =0; i<users.length; i++){
            System.out.println("<li>"+users[i]+"<li>");
        }
    }
}

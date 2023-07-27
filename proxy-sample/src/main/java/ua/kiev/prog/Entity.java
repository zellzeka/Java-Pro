package ua.kiev.prog;

public class Entity implements EntityIntf {
    public String getName() {
        /// --- --- ---- -----
        System.out.println("getName()");
        // --- ---- ---- --------
        return "Vsevolod";
    }

    public int getAge() {
        System.out.println("getAge()");
        return 10;
    }

    public void test() {
        //...
    }
}

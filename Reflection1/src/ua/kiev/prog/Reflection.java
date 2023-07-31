package ua.kiev.prog;

import java.lang.reflect.*;

/*

ArrayList
vector
List
TList

if (Java)
  Collections.sort(a);
else
  a.Sort();

class MyAL extends ArrayList {
  void sort() {
    Collections.sort(this);
  }
}

class X {
	@Save int x;
	@Save int y;
	String s;
}
-->-->

*/

public class Reflection {

	static private final class Test {
		public String p = "Test string";
		private int a = 7;
		protected long b = 8;
		
		public Test() {}
		public Test(int a) { this.a = a; }
		public Test(int a, long b) { this.a = a; this.b = b; }

		public int getA() { return a; }
		public long getB() { return b; }
		public void setA(int a) { this.a = a; }

		public static String getS() { return "Hello"; }
	}
	
	public static void main(String[] args) {
		final Class<?> cls = Test.class;
		
		System.out.println("Class name: " + cls.getName());
		
		System.out.print("Modifiers: ");
		int mods = cls.getModifiers();

		if (Modifier.isPrivate(mods))
		    System.out.print("private "); 
		if (Modifier.isAbstract(mods))
		    System.out.print("abstract "); 
		if (Modifier.isFinal(mods))
		    System.out.print("final ");

		System.out.println("All fields:");
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) { 
		     Class<?> fieldType = field.getType(); 
		     System.out.println("\tName: " + field.getName());
		     System.out.println("\tType: " + fieldType.getName()); 
		} 
		
		System.out.println("Constructors:");
		Constructor<?>[] constrs = cls.getConstructors(); 
		for (Constructor<?> ctr : constrs) { 
		    Class<?>[] paramTypes = ctr.getParameterTypes();
		    if (paramTypes.length == 0) {
				System.out.println("Default");
			} else {
				for (Class<?> paramType : paramTypes) {
					System.out.print(paramType.getName() + " ");
				}
			}
		    System.out.println();
		} 
		
		try {
			//Test t = new Test(1, 3);
			Constructor<?> ctr = cls.getConstructor(int.class, long.class);
			Test t = (Test) ctr.newInstance(1, 3);
			System.out.println("Fields: " + t.getA() + ", " + t.getB());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Method[] methods = cls.getMethods(); 
		for (Method method : methods) { 
		    System.out.println("Name: " + method.getName()); 
		    System.out.println("\tReturn type: " + method.getReturnType().getName()); 
		 
		    Class<?>[] paramTypes = method.getParameterTypes(); 
		    System.out.print("\tParam types:"); 
		    for (Class<?> paramType : paramTypes) { 
		        System.out.print(" " + paramType.getName()); 
		    } 
		    System.out.println(); 
		} 
		
		try {
			Test obj = new Test();
			Method method = cls.getMethod("setA", int.class);
			method.invoke(obj, 55);
			
			System.out.println("A: " + obj.getA());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			Method method = cls.getMethod("getS");
			System.out.println(method.invoke(null));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			Test obj = new Test();
			Method method = cls.getMethod("someUnknownMethod", int.class);
			method.invoke(obj, 55);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*

		class X {
			private int a;
		}

		X x = new X();
		// a?

		 */

		// CyberVision
		try {
			Test obj = new Test();
			Field field = cls.getDeclaredField("a");
			field.setAccessible(true);
			System.out.println("Private field value: " + field.getInt(obj));
			field.setInt(obj, 100);
			System.out.println("New private field value: " + field.getInt(obj));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

import java.util.Date;

/*The classic*/
public class HelloWorld {

	/**
	 */
	public String sayHello(String everyBody){
		String lMsg = "Hello: "+everyBody+" - it is: "+new Date();
		lMsg = lMsg+" - Have a nice day!";
		return lMsg;
	}
}

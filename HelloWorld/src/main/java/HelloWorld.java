import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

/*The classic*/
public class HelloWorld {

	/**
	 * Business Interface
	 */
	public String sayHello(String everyBody){
		String lMsg = "Hello: "+everyBody+" - it is: "+new Date();
		lMsg = lMsg+" - Have a nice day!";
		return lMsg;
	}
	
	/**
	 * Web Interface
	 */
	public String getContent(String pData)throws Exception{
		String lContent = "";
		
		if(pData.equals("page")){
			lContent = readTextFrom(getClass().getResource("/HelloWorld.htm"));
		}else if(pData.equals("elem")){
			lContent = readTextFrom(getClass().getResource("/HelloWorldElem.htm"));
		}else{
			lContent = sayHello(pData);
		}
		
		return lContent;
	}
	
	/**
	 */
	private String readTextFrom(URL pUrl) throws Exception{
		char[] lBuf = new char[1024];
		int lLen = 1;
		InputStreamReader lInReader = null;
		BufferedReader lReader = null;
		StringBuilder lBuilder = new StringBuilder();
		
		try{
			lInReader = new InputStreamReader(pUrl.openStream());
			lReader = new BufferedReader(lInReader);
			
	        while(lLen>0){
	            lLen = lReader.read(lBuf);
	            if(lLen>0){
	            	lBuilder.append(lBuf, 0, lLen);
	            }
	        }
		}finally{
			if(lInReader!=null){lInReader.close();}
			if(lReader!=null){lReader.close();}
		}
		return lBuilder.toString();
	}
}

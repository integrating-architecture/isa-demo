package com.example;

import isa.Bootstrap;
//just two imports
//from one library isa.CoreBoostrap.jar
import isa.ServiceHandler;

/**
 * your application class
 * the world's smallest and simplest modular client server application
 *  - NO special project
 *  - NO special packaging
 *  - NO deployment
 *  - NO runtime
 *  - NO special programming
 *  
 *  get a service - call it - that's it
 */
public class ThirdPartyApplication {

	public static final String LS = System.getProperty("line.separator");

	//the isa service handler
	protected ServiceHandler handler = null;
	//your module services
	protected TestModuleIFace localService = null;
	protected TestServiceIFace remoteService = null;
	
	public ThirdPartyApplication() {
	}

	/**
	 * Three steps to become modular and service oriented
	 * 1. have your own application 
	 */
	public static void main(String[] args) {
		ThirdPartyApplication lApl = new ThirdPartyApplication();
		
		try{
			lApl.go();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 */
	protected void go() throws Exception{
		Object lRet = null;
		
		//2. get a service handler from isa.Bootstrap
		handler = Bootstrap.getInstance("").getServiceHandler();
		
		//3. get a service from a module and call it
		localService = (TestModuleIFace)handler.getService(
				"com.example.ThirdPartyTestModule[version=1.0.0]",
				TestModuleIFace.class);		
		lRet = localService.sayHello("World");
		System.out.println(LS+"LOCAL Service Call returned: "+lRet+LS);
		
		//just one more step
		//and this is a complete client-server application
		System.out.println(LS+"CONNECTING to Server: "+LS);
		handler.connect("gast", "gaast".toCharArray(),
				"http://integrating-architecture.de", "8080",
				"isa.esb.ServiceBrokerWebConnector[version=1.0.0.DEV-SNAPSHOT]");

		//get a remote service from a module and call it
		remoteService = (TestServiceIFace)handler.getService(
				"isa.TestService[version=1.0.0.DEV-SNAPSHOT]",
				TestServiceIFace.class);		
		lRet = remoteService.run("HELLO WORLD");
		System.out.println(LS+"REMOTE Service Call returned: "+lRet+LS);	
	}

/**
 * the interface definitions
 */
	/**
	 * complex business module interface
	 */
	protected interface TestModuleIFace{
		public String sayHello(String pName);
	}
	
	/**
	 * complex remote business module interface
	 */
	protected interface TestServiceIFace{
		public Object run(Object pIn);
	}
}

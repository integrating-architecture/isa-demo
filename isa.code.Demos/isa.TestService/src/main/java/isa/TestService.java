/*Copyright © by Andreas Weidinger - Integrating Architecture.
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

  1. Redistribution and use are in compliance to the license
     or description of conditions named and/or described by the following file or text:
     
     www.integrating-architecture.de/licenses/LICENSE-1.0

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE 
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

package isa;

import static isa.TestServiceConstants.*;
import isa.annotation.Resource;
import isa.annotation.Resource.Type;
import isa.common.Tools;
import isa.common.UnifiedResourceBundle;
import isa.plugin.PlugInDef;

import java.util.Map;

/**<pre>
 * Implementation of an ISA Service Module.
 * 
 * IMPORTANT: Independence hint:
 * Even if this example uses ISA classes there is NO mandatory dependency neither
 * to ISA nor to JEE classes, interfaces structures or to JEE packaging conventions.
 * The annotated isa types could be omitted, as well as the isa resource bundle and Tool.
 * 
 * ISA modules can be designed and implemented completely platform independent.
 * 
 * Although one can use a typesafe interface to call a service it is NOT necessary
 * that the service itself implements the corresponding interface - but it is recommended.   
 * 
 * A client can access this service e.g. by
 *     @Service()
 *     protected TestServiceIFace testService = null;
 *</pre>
 * @author A.Weidinger
 */
public class TestService {

	protected static final String LS = Tools.LS;

	protected Tools Tool = Tools.getInstance(TestService.class);
	protected UnifiedResourceBundle rb = null;

	@Resource()
	protected PlugInHandler handler = null;
	@Resource(Type.CONFIG)
	protected Map config = null;
	
	/**
	 */
	public TestService() {
	}

	/**
	 * All annotated values are also available through the activation callback
	 */
	public void onInstanceActivation(Object pCtx)throws Exception{
		rb = (UnifiedResourceBundle)((Map)pCtx).get(Constants.PlugInResourceBundle);
	}

	/**
	 * The interface method of this service
	 */
	public Object run(Object pInput) throws Exception {
		Object lRet = "";
		Object[] lValues = new Object[5];

		PlugInDef lDef = new PlugInDef(config.get(Constants.PlugInDef).toString());
		lValues[0] = lDef.getVName();
				
		if(config==null){
			lValues[1] = "Stateless";
		}else{
			lValues[1] = "Stateful";
		}
	
		//collect some values
		lValues[2] = pInput;
		lValues[3] = "pInput.toString().toLowerCase()";
		lValues[4] = pInput.toString().toLowerCase();
				
		//get the resource bundle based message string - MessageText
		//and populate it with the collected values
		lRet = rb.get(MessageText, lValues);
		
		//and return
		return lRet.toString()+LS+this.getMemInfo();
	}
	
	/**
	 */
	protected String getMemInfo(){
		StringBuffer lBuf = new StringBuffer(rb.get(Mem));
		
		Runtime lRt = Runtime.getRuntime();
		lBuf.append(LS);
		lBuf.append(rb.get(FreeMem)).append(" ").append(lRt.freeMemory()/1000).append(LS); 
		lBuf.append(rb.get(MaxMem)).append(" ").append(lRt.maxMemory()/1000).append(LS);
	    
		return lBuf.toString();
	}
}

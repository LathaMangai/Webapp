package com.example.Webex;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javacg.stat.JCallGraph;



@RestController
public class HomeController {
	
	

	@GetMapping(path="/{className}/{methodName}")
	public String getClassAndMethod (@PathVariable("className")String className,@PathVariable("methodName")String methodName) throws Exception

	{
			
		JCallGraph.main(new String[]{ methodName});
  	    
		return "Class Name :"+className+"  "+"Method Name :"+methodName;
		
	}
}


package org.lsqt.codegen.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConsoleController {
	
	@RequestMapping("/console")
	public String console(){
		return "index";
	}

}

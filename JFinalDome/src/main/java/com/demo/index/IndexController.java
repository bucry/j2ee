package com.demo.index;

import com.jfinal.core.Controller;

public class IndexController extends Controller {
	public void index() {
		render("index.html");
	}
	
	
	public void indexBack() {
		render("index1.html");
	}
}




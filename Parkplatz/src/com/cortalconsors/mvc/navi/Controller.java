package com.cortalconsors.mvc.navi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {

	public String execute(HttpServletRequest request,
					  HttpServletResponse response, StringBuffer message) throws Exception;
}

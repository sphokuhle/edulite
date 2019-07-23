package edusystem.eduLite.rest;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/rest/*")
public class RestFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		//do nothing
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request2 = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.addHeader("Access-Control-Allow-Origin","*");
			resp.addHeader("Access-Control-Allow-Methods","GET,POST");
			resp.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
			if ( request2.getMethod().equals("OPTIONS") ) {
				resp.setStatus(HttpServletResponse.SC_OK);
				return;
			}

			chain.doFilter(request2, response);
		} finally {
			//do nothing
		}
		
	}

	public void destroy() {
		//do nothing	
	}
}

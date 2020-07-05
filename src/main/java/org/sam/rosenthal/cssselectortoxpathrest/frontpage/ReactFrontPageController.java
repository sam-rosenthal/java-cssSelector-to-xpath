package org.sam.rosenthal.cssselectortoxpathrest.frontpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactFrontPageController
{
	/**	https://stackoverflow.com/questions/58895116/springboot-with-react-rest-api-clashes-with-react-routes-on-refresh
	**	Because spring boot was intercepting the page request, had to add controller to redirect back to index.html
	**/
    @RequestMapping(value={"/TestPage"})
    public String HomePage() {
        return "index.html";
    }
}
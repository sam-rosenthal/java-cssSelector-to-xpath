package org.sam.rosenthal.cssselectortoxpathrest.restservice;
import java.util.List;

import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.basetestcases.BaseCssSelectorToXpathTestCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cssSelectorToXpath")
@CrossOrigin	
public class CssSelectorToXpathController {
	@PostMapping("/convert")
	@ResponseBody
	public ResponseEntity<Object> convert(@RequestBody CssSelectorIn cssSelector) throws CssSelectorToXPathConverterException{
		try {
			String xpath =  new CssElementCombinatorPairsToXpath().convertCssSelectorStringToXpathString(cssSelector.getCssSelector());
			return new ResponseEntity<>(new XpathOut(xpath), HttpStatus.OK);
		}
		catch (CssSelectorToXPathConverterException e)
		{  
			return new ResponseEntity<>(new InvalidCssSelector(e), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/testcases")
	public List<BaseCssSelectorToXpathTestCase> testCases(){
		return BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathTestCases(false);
	}
	
	@GetMapping("/version")
	public String version(){
		return new CssElementCombinatorPairsToXpath().getVersionNumber();
	}
}
<h1><a href="https://github.com/sam-rosenthal/" target="_blank"><img src="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/src/main/webapp/fav.png" align="left" height="40" width="40"></a> java-cssSelector-to-xpath</h1>

<a href="https://travis-ci.org/sam-rosenthal/java-cssSelector-to-xpath" target="_blank"><img src="https://travis-ci.org/sam-rosenthal/java-cssSelector-to-xpath.svg?branch=master" align="left"> </a>

<img src="https://img.shields.io/badge/jdk-8-lightgray.svg" align="left"> </a>

<a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/License.txt" target="_blank"><img src="https://badges.frapsoft.com/os/mit/mit.svg?v=102" align="left"> </a>

<a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath" target="_blank"><img src="https://badges.frapsoft.com/os/v1/open-source.svg?v=102" align="left"> </a>

<br/>
<p>Java tool for transforming CSS Selector strings to XPath strings. </p> 
<a href="https://css-selector-to-xpath.appspot.com" target="_blank">Visit the website that implements this tool.</a>

<br/>
<b><a style="font-size:24px" href="https://sam-rosenthal.github.io" target="_blank"> Need a 2019 summer intern? </a></b>
<h3> Table of Contents
  <ul>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#Implementation" target="_blank"> CSS Selector to XPath Conversion Implementation Notes</a>     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#Dependencies" target="_blank"> Dependencies</a>	     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#Installation" target="_blank"> Installation </a>     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#Usage" target="_blank"> Usage</a>	     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#Todos" target="_blank"> TODOs</a>	     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#links" target="_blank"> Helpful Links/More Info</a>   </li>
        <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#about" target="_blank"> About me </a>   </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/README.md#license" target="_blank"> License</a>   </li>
  <ul>
</h3>

<h2 id="Implementation"> CSS Selector to XPath Conversion Implementation Notes  </h2>
			<ul>
				<li>CSS Selector To XPath has been implemented for all 
					<a href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Simple_selectors" target="_blank">Simple Selectors </a>
					<a href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Combinators" target="_blank"> and Combinators. </a>
					<a href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Pseudo-classes" target="_blank">Pseudo-Classes</a>
					have not been implemented.  </li>
				<ul>
					<li> Not all Pseudo-Class CSS Selectors can be converted to XPaths. </li>
					<li> A possible future enhancement is to implement a partial subset of CSS Selector Pseudo-Classes. </li>
				</ul>
				<li>Chrome Browser was used as reference implementation.</li>
				<ul> 
					<li>All base CSS Selectors and corresponding XPaths were tested cases using 
						<a href="https://yizeng.me/2014/03/23/evaluate-and-validate-xpath-css-selectors-in-chrome-developer-tools/" target="_blank">Chrome Browser's Developer Tools.</a>
					</li>
				 </ul>
				<li>Additional restrictions</li>
					<ul> 		
						<li>All names and values (Elements and Attributes) are case sensitive</li>
						<li>Element names and Attribute names are restricted to the following regular expression: -?[_a-zA-Z]+[_a-zA-Z0-9-]*</li>
						<li>Attribute values are restricted to the following regular expression: [_a-zA-Z0-9- ]+</li>
					</ul>
			</ul>

<h2 id="Dependencies"> Dependencies </h2>
<p> This project is a pure jave applicication/API. It requries Java JDK 8 and no external jars. Within this project's baseline, there is a <a href="https://css-selector-to-xpath.appspot.com" target="_blank"> web-based implementation</a> that depends on <a href="https://wicket.apache.org">  Wicket 8.0. </a> I consider the webpage an example implementation of this project. As a result I don' consider the dependencies for the websitess a requirement for the OSS.</p>
<p>

<h2 id="Installation"> Installation </h2>
<p> To create the project's jar file, run the following maven command: <p/>
<pre>
  <code>mvn install</code>
</pre>
<p> The jar file will be installed in <b>target</b> subdirectory and the name will include the corresponding version number of this project <p/>

<h2 id="Usage"> Usage </h2>
<ul> <li> Usage as a Java Application: 
<pre>
  <code> 
    java -cp <b>org.sam.rosenthal.java-cssSelector-to-xpath-&ltversion number&gt.jar org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath</b> &ltCSS Selector String&gt 
    -	Converts a CSS Sector String to a Xpath String
    java –cp <b>org.sam.rosenthal.java-cssSelector-to-xpath-&ltversion number&gt.jar  org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath</b> -v{ersion}
    -	Displays  the version number of java-cssSlector-to-xpath
    Java –cp <b>org.sam.rosenthal.java-cssSelector-to-xpath-&ltversion number&gt.jar org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath</b> -h{elp}
    -	Displays this help usage text 
  </code>
</pre> </li>
<li> Usage as a Java method call:
<pre>
	<code>
	public String convertCssSelectorToXpath(String cssSelector) throws CssSelectorToXPathConverterException
	{
		return new <b>CssElementCombinatorPairsToXpath().convertCssSelectorStringToXpathString</b>(cssSelector);
	}
	</code>
</pre>
</li> 
</ul>
<h2><a id="TODOs"></a>Todos</h2>
<ul>
  <li>Handle Psuedo-classes</li>
</ul>

<h2><a id="links"></a>Helpful Links/More Info</h2>
  <ul>
		<li><a href="https://en.wikipedia.org/wiki/Cascading_Style_Sheets" target="_blank">Cascading Style Sheets-Wiki</a></li>
		<li><a href="https://en.wikipedia.org/wiki/XPath" target="_blank">XPath-Wiki</a></li>
		<li><a href="https://en.wikibooks.org/wiki/XPath/CSS_Equivalents" target="_blank">XPath/CSS Equivalents</a></li>
		<li><a href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors" target="_blank">MDN web docs CSS Selectors</a></li>
		<li><a href="https://developer.mozilla.org/en-US/docs/Web/XPath">MDN web docs XPath</a></li>
		<li><a href="https://www.w3schools.com/cssref/trysel.asp" target="_blank">W3Schools CSS Reference</a></li>
		<li><a href="https://css-tricks.com/almanac/" target="_blank">CSS Tricks Almanac</a></li>
		<li><a href="https://yizeng.me/2014/03/23/evaluate-and-validate-xpath-css-selectors-in-chrome-developer-tools/" target="_blank">Evaluate and validate XPath/CSS selectors in Chrome Developer Tools</a></li>		
  </ul>
      
<h2><a id="about">About me </h2>
<a href="https://sam-rosenthal.github.io" target="_blank"> Sam Rosenthal - Cornell Engineering '21 </a>	

<h2><a id="license"> License </h2>
<p> This project is licensed under the <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/License.txt" target="_blank">MIT License. </a> </p>

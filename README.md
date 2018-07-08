<h1><a href="https://github.com/sam-rosenthal/" target="_blank"><img src="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/master/src/main/webapp/fav.png" align="left" height="40" width="40"></a> java-cssSelector-to-xpath</h1>

<a href="https://travis-ci.org/sam-rosenthal/java-cssSelector-to-xpath" target="_blank"><img src="https://travis-ci.org/sam-rosenthal/java-cssSelector-to-xpath.svg?branch=master" align="left"> </a>
<br>
<p>Java tool transforms CSS Selector strings to XPath strings. </p> 
<a href="https://css-selector-to-xpath.appspot.com" target="_blank">Visit the website that implements this tool.</a>

<h3> Table of Contents
  <ul>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#Implementation" target="_blank"> CSS Selector to XPath Conversion Implementation Notes</a>     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#Dependencies" target="_blank"> Dependencies</a>	     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#Installation" target="_blank"> Installation </a>     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#Usage" target="_blank"> Usage</a>	     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#Todos" target="_blank"> TODOs</a>	     </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#links" target="_blank"> Helpful links</a>   </li>
        <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#about" target="_blank"> About me </a>   </li>
    <li> <a href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md#license" target="_blank"> License</a>   </li>
  <ul>
</h3>

<h2 id="Implementation"> CSS Selector to XPath Conversion Implementation Notes  </h2>

<h2 id="Dependencies"> Dependencies </h2>
<p> This project is a pure jave applicication/API. It requries Java JDK 8 and no external jars. Within this project's baseline, there is a <a href="https://css-selector-to-xpath.appspot.com" target="_blank"> web-based implementation</a> that depends on <a href="https://wicket.apache.org">  Wicket 8.0. </a> I consider the webpage an example implementation of this project. As a result I don' consider the dependencies for the websitess a requirement for the OSS.</p>
<p>

<h2 id="Installation"> Installation </h2>
<p> To create the project's jar file, run the following maven command: <p/>
<pre>
  <code>mvn install</code>
</pre>
<p> The jar file will be installed in <b>target/</b> subdirectory and the name will include the corresponding version number of this project <p/>


<h2 id="Usage"> Usage </h2>
<pre>
  <code> 
    java -cp org.sam.rosenthal.java-cssSelector-to-xpath-<version number> org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath <CSS Selector String>
    -	Converts a CSS Sector String to a Xpath String
    java –cp org.sam.rosenthal.java-cssSelector-to-xpath-<version number> org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath -v{ersion}
    -	Displays  the version number of java-cssSlector-to-xpath
    Java –cp org.sam.rosenthal.java-cssSelector-to-xpath-<version number> org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath -h{elp}
    -	Displays this help usage text 
  </code>
</pre>
  

<h2><a id="TODOs"></a>Todos</h2>
<ul>
  <li>Handle Psuedo-classes</li>
</ul>

<h2><a id="links"></a>Helpful links</h2>

<h2><a id="about">About me </h2>
<a href="https://sam-rosenthal.github.io" target="_blank"> Sam Rosenthal - Cornell Engineering '21 </a>	

<h2><a id="license"> License </h2>
Finally, include a section for the license of your project. For more information on choosing a license, check out GitHub’s licensing guide!

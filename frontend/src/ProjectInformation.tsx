import React from 'react';
import { Link } from 'react-router-dom';

export const DevelopedBy: JSX.Element = (
  <fieldset className="row">
    <legend id="bySamRosenthal">
      {' '}
      <h3 id="developedBy">
        Developed by{' '}
        <a
          href="https://github.com/sam-rosenthal"
          rel="noopener noreferrer"
          target="_blank"
          style={{ color: 'rgb(41, 101, 241)' }}
        >
          Sam Rosenthal
        </a>
      </h3>
    </legend>
    <h4>
      <a
        style={{ fontSize: '20px', color: 'rgb(41, 101, 241)', textDecoration: 'underline' }}
        href="https://sam-rosenthal.github.io"
        rel="noopener noreferrer"
        target="_blank"
      >
        <b>Looking for a 2021 Cornell University graduate?</b>
      </a>
    </h4>
    <br />
    <h5>
      If you have any suggestions, questions, or feedback feel free to email me at:{' '}
      <a href="mailto:ser259@cornell.edu" style={{ color: 'rgb(41, 101, 241)' }}>
        ser259@cornell.edu
      </a>
    </h5>
  </fieldset>
);

export const HelpfulLinks: JSX.Element = (
  <fieldset>
    <legend id="HelpfulLinks">Helpful links</legend>
    <ul>
      <li>
        <a href="https://en.wikipedia.org/wiki/Cascading_Style_Sheets" rel="noopener noreferrer" target="_blank">
          Cascading Style Sheets-Wiki
        </a>
      </li>
      <li>
        <a href="https://en.wikipedia.org/wiki/XPath" rel="noopener noreferrer" target="_blank">
          XPath-Wiki
        </a>
      </li>
      <li>
        <a href="https://en.wikibooks.org/wiki/XPath/CSS_Equivalents" rel="noopener noreferrer" target="_blank">
          XPath/CSS Equivalents
        </a>
      </li>
      <li>
        <a
          href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors"
          rel="noopener noreferrer"
          target="_blank"
        >
          MDN web docs CSS Selectors
        </a>
      </li>
      <li>
        <a href="https://developer.mozilla.org/en-US/docs/Web/XPath" rel="noopener noreferrer" target="_blank">
          MDN web docs XPath
        </a>
      </li>
      <li>
        <a href="https://www.w3schools.com/cssref/trysel.asp" rel="noopener noreferrer" target="_blank">
          W3Schools CSS Reference
        </a>
      </li>
      <li>
        <a href="https://css-tricks.com/almanac/" rel="noopener noreferrer" target="_blank">
          CSS Tricks Almanac
        </a>
      </li>
      <li>
        <a
          href="https://yizeng.me/2014/03/23/evaluate-and-validate-xpath-css-selectors-in-chrome-developer-tools/"
          rel="noopener noreferrer"
          target="_blank"
        >
          Evaluate and validate XPath/CSS selectors in Chrome Developer Tools
        </a>
      </li>
    </ul>
  </fieldset>
);

export const ImplementationNotes: JSX.Element = (
  <fieldset>
    <legend id="ImplementationNotes">Implementation notes</legend>
    <ul>
      <li>
        CSS Selector To XPath have been implemented for all{' '}
        <a
          href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Simple_selectors"
          rel="noopener noreferrer"
          target="_blank"
        >
          {' '}
          Simple Selectors{' '}
        </a>
        <a
          href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Combinators"
          rel="noopener noreferrer"
          target="_blank"
        >
          {' '}
          and Combinators.
        </a>
      </li>
      <li>
        <h4>
          The following{' '}
          <a
            href="https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Pseudo-classes"
            rel="noopener noreferrer"
            target="_blank"
          >
            Pseudo-Classes
          </a>{' '}
          have been implemented.
          <ul>
            <li>
              <b>:empty, :first-child, :last-child, :only-child, :first-of-type, :last-of-type, :only-of-type </b>
            </li>
            <li>
              <span style={{ color: 'red', fontStyle: 'italic' }}>(*NEW*)</span> <b>:nth-child</b>(<i>expr</i>
              ), <b>:nth-last-child</b>(<i>expr</i>
              ), <b>:nth-of-type</b>(<i>expr</i>
              ), <b>:nth-last-of-type</b>(<i>expr</i>
              ) <br />
              expr = {'{'}
              <i>odd | even | xn+y, where x and y are integers</i>
              {'}'}
            </li>
          </ul>
        </h4>
      </li>
      <li>Future enhancements will be to implement additional CSS Selector Pseudo-Classes. </li>
      <ul>
        <li> Note: not all Pseudo-Class CSS Selectors can be converted to XPaths </li>{' '}
      </ul>
      <li>Chrome Browser was used as reference implementation.</li>
      <ul>
        <li>
          All base CSS Selectors and corresponding XPaths were tested cases using{' '}
          <a
            href="https://yizeng.me/2014/03/23/evaluate-and-validate-xpath-css-selectors-in-chrome-developer-tools/"
            rel="noopener noreferrer"
            target="_blank"
          >
            Chrome Browser&#39;s Developer Tools.
          </a>
        </li>
      </ul>
      <li>Additional restrictions</li>
      <ul>
        <li>All names and values (Elements and Attributes) are case sensitive</li>
        <li>
          Element names and Attribute names are restricted to the following regular expression:
          -?[_a-zA-Z]+[_a-zA-Z0-9-]*
        </li>
        <li>Attribute values are restricted to the following regular expression: [-_.#a-zA-Z0-9:\\/ ]+</li>
      </ul>
    </ul>
  </fieldset>
);

export const TestingNotes: JSX.Element = (
  <fieldset>
    <legend id="TestingNotes">Testing notes</legend>
    <p>
      <a
        href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md"
        rel="noopener noreferrer"
        target="_blank"
      >
        java-cssSelector-to-xpath
      </a>{' '}
      was extensively tested using{' '}
      <a
        href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/tree/samdev/src/test/java/org/sam/rosenthal/cssselectortoxpath/utilities"
        rel="noopener noreferrer"
        target="_blank"
      >
        {' '}
        JUnit Tests
      </a>{' '}
      and{' '}
      <a
        href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/tree/samdev/src/test/java/org/sam/rosenthal/selenium"
        rel="noopener noreferrer"
        target="_blank"
      >
        Selenium tests
      </a>
      . An{' '}
      <Link to="/TestPage" rel="noopener noreferrer" target="_blank">
        example test page
      </Link>{' '}
      of CSS styling was used to test CSS Selectors and the corresponding XPaths generated by this OSS. Since it was
      developed on a Windows platform, the Selenium tests run Chrome, Firefox, and Edge browsers.
    </p>
  </fieldset>
);

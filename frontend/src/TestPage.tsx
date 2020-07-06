import React from 'react';
import './TestPage.css';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { BASE_REST_URL } from './BASE_REST_URL';

interface testData {
  cssSelector: string;
  expectedXpath: string;
  name: string;
}

const testContent: { [key: string]: JSX.Element } = {
  typeSelector: <div>typeSelector</div>,
  universalSelector: <span id="dummy">universalSelector</span>,
  idSelector: <span id="idSelector">idSelector</span>,
  classSelector: <form className="classSelector"> classSelector</form>,
  attribute: <a href="https://css-selector-to-xpath.appspot.com">attribute</a>,
  attributeValueWithoutQuotes: <li> attributeValueWithoutQuotes </li>,
  carrotEqualAttribute: (
    <a data-alt="sam rosenthal" href="https:css-selector-to-xpath.appspot.com">
      {' '}
      carrotEqualAttribute
    </a>
  ),
  starEqualAttribute: (
    <a data-alt="cssSelector to Xpath " href="//placehold.it/150/150/abstract">
      starEqualAttribute
    </a>
  ),
  moneySignEqualAttribute: <a href="//s3-us-west-2.amazonaws.com/s.cdpn.io/652/example.pdf">moneySignEqualAttribute</a>,
  tildaEqualAttribute: (
    <a data-alt="converter website" href="//placehold.it/150/150">
      tildaEqualAttribute
    </a>
  ),
  pipeEqualAttribute: <li data-years="1900-2000">pipeEqualAttribute</li>,
  equalAttribute: <a href="https://css-selector-to-xpath.appspot.com">equalAttribute</a>,
  descendantCombinator: (
    <div>
      <h1>
        <a href="#descendantCombinator"> descendantCombinator</a>
      </h1>
    </div>
  ),
  childCombinator: (
    <div>
      <span>childCombinator</span>
    </div>
  ),
  adjacentSiblingCombinator: (
    <>
      <div></div>
      <span>adjacentSiblingCombinator</span>
    </>
  ),
  generalSiblingCombinator: (
    <>
      <div></div>
      <form></form>
      <h1>generalSiblingCombinator</h1>
    </>
  ),
  empty: <span></span>,
  'first-child': (
    <>
      <div>first-child</div> <label>goodbye</label> <div> goodbye2 </div>
    </>
  ),
  'last-child': (
    <>
      <div>aaabbbccc</div>
      <label>eeefff</label>
      <span>last-child</span>
    </>
  ),
  'only-child': <form>only-child</form>,
  'first-of-type': (
    <>
      <form>aaa</form>
      <p>first-of-type</p>
      <p>bbb</p>
      <p>ccc</p>
    </>
  ),
  'last-of-type': (
    <>
      <h1>aaa</h1> <p>bbb</p> <h1>last-of-type</h1>
    </>
  ),
  'only-of-type': (
    <>
      <h1>aaa</h1> <span>only-of-type</span>
      <p>bbb</p>{' '}
    </>
  ),
  'nth-child_3n': (
    <>
      <ul>
        <li>aa</li>
        <li>bbb</li>
        <li>nth-child_3n</li>
        <li>dddd</li>
        <li>eeeee</li>
        <li>nth-child_3n</li>
        <li>ffffff</li>
      </ul>
    </>
  ),
  'nth-child_even': (
    <>
      <ul>
        <li>aaa</li>
        <li>nth-child_even</li>
        <li>ccc</li>
        <li>nth-child_even</li>
        <li>dddd</li>
        <li>nth-child_even</li>
        <li>ggg</li>
      </ul>
    </>
  ),
  'nth-last-child_odd': (
    <>
      <ul>
        <li>aaa</li>
        <li>nth-last-child_odd</li>
        <li>ccc</li>
        <li>nth-last-child_odd</li>
        <li>dddd</li>
        <li>nth-last-child_odd</li>
      </ul>
    </>
  ),
  'nth-last-child_3': (
    <>
      <ul>
        <li>aaa</li>
        <li>bbb</li>
        <li>ccc</li>
        <li>dddd</li>
        <li>nth-last-child_3</li>
        <li>fff</li>
        <li>ggg</li>
      </ul>
    </>
  ),
  'nth-of-type_n_2': (
    <>
      <span>aaa</span>
      <div>bbb</div>
      <span>nth-of-type_n_2</span>
      <div>ddd</div>
      <span>nth-of-type_n_2</span>
      <div>fff</div>
      <span>nth-of-type_n_2</span>
      <div>hhh</div>
      <span>nth-of-type_n_2</span>
      <span>nth-of-type_n_2</span>
      <span>nth-of-type_n_2</span>
    </>
  ),
  'nth-of-type_3n_1': (
    <>
      <span>nth-of-type_3n_1</span>
      <div>bbb</div>
      <span>cccc</span>
      <div>ddd</div>
      <span>eee</span>
      <div>fff</div>
      <div>hhh</div>
      <span>nth-of-type_3n_1</span>
      <span>jjj</span>
      <span>kk</span>
      <div>lll</div>
      <span>nth-of-type_3n_1</span>
    </>
  ),
  'nth-of-type_-5n_1': (
    <>
      <span>nth-of-type_-5n_1</span>
      <div>bbb</div>
      <span>cccc</span>
      <div>ddd</div>
      <span>eee</span>
      <div>fff</div>
    </>
  ),
  'nth-last-of-type_3n-1': (
    <>
      <span>nth-last-of-type_3n-1</span>
      <div>bbb</div>
      <span>ccc</span>
      <div>ddd</div>
      <span>eee</span>
      <div>fff</div>
      <span>nth-last-of-type_3n-1</span>
      <span>hhh</span>
      <div>iii</div>
      <span>jjj</span>
      <span>nth-last-of-type_3n-1</span>
      <span>kk</span>
    </>
  ),
  'nth-last-of-type_-3n_7': (
    <>
      <span>aaa</span>
      <div>bbb</div>
      <span>nth-last-of-type_-3n_7</span>
      <div>ddd</div>
      <span>eee</span>
      <div>fff</div>
      <span>ggg</span>
      <span>nth-last-of-type_-3n_7</span>
      <div>iii</div>
      <span>jjj</span>
      <span>kk</span>
      <span>nth-last-of-type_-3n_7</span>
    </>
  ),
};

export default class TestPage extends React.Component {
  constructor(prop: string) {
    super(prop);
    this.state = <></>;
    this.create();
  }
  create(): void {
    axios.get(`${BASE_REST_URL}/testcases`).then((res) => {
      const testCaseData: Array<testData> = res.data;
      // console.log(testCaseData.map((testCase) => testCase));

      this.setState(
        <div>
          {testCaseData.map((testCase) => (
            <div key={testCase.name} className="testcase">
              <label style={{ margin: '0px' }}>
                <b>
                  Test case name= {testCase.name}, CSS Selector=
                  {testCase.cssSelector}, XPath=
                  {testCase.expectedXpath}
                </b>
              </label>
              <div id={testCase.name}>{testContent[testCase.name]}</div>
            </div>
          ))}
        </div>,
      );
    });
  }

  render(): JSX.Element {
    return (
      <>
        <div>
          <div>
            <Link to="/">Homepage</Link>
          </div>
          <div>
            <h1>Examples CSS Selector Used to Test Basic CSS Selectors</h1>
            <h2>
              Selenium tests have been written to verify CSS-Selectotrs-To-Xpath functionality
              <br /> Both CSS Selectors and XPaths generated by calling converCssStringToXpahString() are used to
              validate the software.
            </h2>
            <br />
            <br />
          </div>
          {this.state}
        </div>
      </>
    );
  }
}

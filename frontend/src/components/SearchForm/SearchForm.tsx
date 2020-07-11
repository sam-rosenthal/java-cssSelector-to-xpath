import React, { useState } from 'react';
import axios from 'axios';
import Output from '../Output/Output';
import { errorDiv } from '../Output/XpathError';
import './SearchForm.css';
import { BASE_REST_URL } from '../../BASE_REST_URL';
import { Collapse } from '@material-ui/core';

export default function SearchForm(): JSX.Element {
  const [cssSelector, setCssSelector] = useState('');
  const [versionNumber, setVersionNumber] = useState(null);
  const [inProp, setInProp] = useState(false);

  const textInput = React.createRef<HTMLInputElement>();
  const output = React.createRef<Output>();

  function getVersionNumber(): void {
    axios.get(`${BASE_REST_URL}/version`).then((res) => {
      // const versionNumber: String = res.data;
      // console.log(res.data);
      setVersionNumber(res.data);
    });
  }

  if (!versionNumber) {
    getVersionNumber();
  }

  function handleChange(event: React.ChangeEvent<HTMLInputElement>): void {
    setCssSelector(event.target.value);
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>): void {
    event.preventDefault();
    const data = { cssSelector: cssSelector };
    console.log(inProp);
    axios
      .post(`${BASE_REST_URL}/convert`, data)
      .then((res) => {
        // console.log(res);
        const xpath = res.data.xpath;
        // console.log(xpath);
        output.current?.setState({ cssSelector: cssSelector, xpath: xpath });
        cssSelector !== '' ? setInProp(true) : setInProp(false);
      })
      .catch((error) => {
        const errorMessage = error.response.data.message;
        console.log(error.response);
        if (error.response.status === 400) {
          console.log('caught');
          output.current?.setState({
            cssSelector: cssSelector,
            xpath: errorDiv(errorMessage),
          });
        } else {
          console.log('uncaught');
          output.current?.setState({
            cssSelector: cssSelector,
            xpath: errorDiv(
              'Unexpected error, Status code = ' +
                error.response.status +
                ', Status text = ' +
                error.response.statusText,
            ),
          });
        }
        cssSelector !== '' ? setInProp(true) : setInProp(false);
      });

    textInput.current?.select();
  }

  return (
    <div className="container" style={{ display: 'flex', width: '65%', alignItems: 'center' }}>
      <form onSubmit={handleSubmit} className="w-100 mt-5">
        <div className="header">
          <h1 className="text-center display-4">CSS Selector to XPath Converter</h1>
          <h5 className="text-center">
            (Powered by{' '}
            <a
              href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md"
              rel="noopener noreferrer"
              target="_blank"
              style={{ color: 'rgb(41, 101, 241)' }}
            >
              java-cssSelector-to-xpath
            </a>{' '}
            {versionNumber})
          </h5>
        </div>
        <br />
        <div id="searchBox" className="d-flex input-group w-100 ">
          <input
            className="form-control"
            type="text"
            value={cssSelector}
            onChange={handleChange}
            ref={textInput}
            placeholder="Enter a CSS Selector to convert to XPath"
          />
          <div className="input-group-append">
            <button
              className="btn btn-primary"
              type="submit"
              id="submitButton"
              style={{ backgroundColor: 'rgb(41, 101, 241)', borderColor: 'rgb(41, 101, 241)' }}
            >
              Convert
            </button>
          </div>
        </div>{' '}
        <Collapse in={inProp} collapsedHeight={100}>
          <Output inputTextBox={textInput} ref={output} />
        </Collapse>
      </form>
    </div>
  );
}

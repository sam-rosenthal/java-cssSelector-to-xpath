import React from 'react';
import './ForkMe.css';
export default class ForkMe extends React.Component {
  render(): JSX.Element {
    return (
      <span id="forkongithub">
        <a
          href="https://github.com/sam-rosenthal/java-cssSelector-to-xpath"
          style={{ boxSizing: 'content-box' }}
          rel="noopener noreferrer"
          target="_blank"
        >
          Fork me on GitHub
        </a>
      </span>
    );
  }
}

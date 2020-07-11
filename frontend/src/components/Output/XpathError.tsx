import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';

export function xpathDiv(xpath: string, toolTip: JSX.Element): JSX.Element {
  return (
    <>
      <td>
        <h2>
          <b>XPath</b>
        </h2>
      </td>
      <td>
        <div id="xpathOutputGridText" style={{ overflowX: 'auto', width: '100%' }}>
          {xpath}
        </div>
      </td>
      <td>{toolTip}</td>
    </>
  );
}

export function errorDiv(error: string): JSX.Element {
  return (
    <>
      <td style={{ padding: '16px', maxWidth: 'initial', overflowX: 'auto', width: '100%' }}>
        <div>
          <FontAwesomeIcon icon={faTimesCircle} color="red" />
          {'   '}
          <b id="errorMessage" style={{ color: 'red' }}>
            Error: {error}
          </b>
        </div>
      </td>
    </>
  );
}

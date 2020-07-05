import React from 'react';
import copy from 'copy-to-clipboard';
import { Tooltip } from '@material-ui/core';
import { Container, Table } from 'react-bootstrap';
import './OutputTable.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCopy } from '@fortawesome/free-regular-svg-icons';
import { xpathDiv } from './XpathError';

interface OutputState {
  cssSelector: string;
  xpath: string | JSX.Element;
  cssSelectorCopyLabelShow: boolean;
  cssSelectorCopyLabel: string;
  xpathCopyLabelShow: boolean;
  xpathCopyLabel: string;
}
interface OutputProp {
  inputTextBox: React.RefObject<HTMLInputElement>;
}

export default class Output extends React.Component<OutputProp, OutputState> {
  constructor(prop: OutputProp) {
    super(prop);
    this.state = {
      cssSelector: '',
      xpath: '',
      cssSelectorCopyLabelShow: false,
      cssSelectorCopyLabel: 'Copy CSS Selector',
      xpathCopyLabelShow: false,
      xpathCopyLabel: 'Copy XPath',
    };
  }
  getXpathToolTip(xpath: string): JSX.Element {
    return (
      <Tooltip open={this.state.xpathCopyLabelShow} title={this.state.xpathCopyLabel}>
        <button
          className="btn"
          type="button"
          onClick={() => {
            copy(xpath);
            this.setState({ xpathCopyLabel: 'Copied!' });
            // this.props.inputTextBox.current?.select();
          }}
          onMouseOver={() => this.setState({ xpathCopyLabelShow: true })}
          onMouseEnter={() => this.setState({ xpathCopyLabel: 'Copy XPath' })}
          onMouseOut={() => this.setState({ xpathCopyLabelShow: false })}
        >
          <FontAwesomeIcon icon={faCopy} />
        </button>
      </Tooltip>
    );
  }

  render(): JSX.Element {
    return (
      <Container style={{ paddingTop: '16px', paddingBottom: '16px', paddingLeft: '0px', paddingRight: '0px' }}>
        {/* <script>console.log({this.state.cssSelector !== ''});</script> */}

        {this.state.cssSelector !== '' && (
          <Table bordered responsive>
            <tbody>
              <tr>
                <td>
                  <h2>
                    <b>CSS Selector</b>
                  </h2>
                </td>
                <td>
                  <div id="cssInputGridText" style={{ overflowX: 'auto' }}>
                    {this.state.cssSelector}
                  </div>
                </td>
                <td>
                  <Tooltip open={this.state.cssSelectorCopyLabelShow} title={this.state.cssSelectorCopyLabel}>
                    <button
                      className="btn"
                      type="button"
                      onClick={() => {
                        copy(this.state.cssSelector);
                        this.setState({ cssSelectorCopyLabel: 'Copied!' });
                        // this.props.inputTextBox.current?.select();
                      }}
                      onMouseOver={() => this.setState({ cssSelectorCopyLabelShow: true })}
                      onMouseEnter={() => this.setState({ cssSelectorCopyLabel: 'Copy CSS Selector' })}
                      onMouseOut={() => this.setState({ cssSelectorCopyLabelShow: false })}
                    >
                      <FontAwesomeIcon icon={faCopy} />
                    </button>
                  </Tooltip>
                </td>
              </tr>
              <tr>
                {typeof this.state.xpath === 'string'
                  ? xpathDiv(this.state.xpath, this.getXpathToolTip(this.state.xpath))
                  : this.state.xpath}
              </tr>
            </tbody>
          </Table>
        )}
      </Container>
    );
  }
}

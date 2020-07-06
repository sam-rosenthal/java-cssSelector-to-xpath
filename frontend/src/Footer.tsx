import React from 'react';
import { ImplementationNotes, HelpfulLinks, TestingNotes, DevelopedBy } from './ProjectInformation';
import './Footer.css';

export default function About(): JSX.Element {
  return (
    <>
      <hr />
      <div className="row" id="footer">
        <div className="col-md-4 col-sm-12 order-md-1 order-sm-2 order-2" style={{ height: 'min-content' }}>
          {ImplementationNotes}
        </div>
        <div className="col-md-4 col-sm-12 order-md-2 order-sm-1 order-1" style={{ textAlign: 'center' }}>
          {DevelopedBy}
        </div>
        <div className="col-md-4 col-sm-12 order-md-3 order-3 ">
          {TestingNotes}
          {HelpfulLinks}
        </div>
      </div>
    </>
  );
}

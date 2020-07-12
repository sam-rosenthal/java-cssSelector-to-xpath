import React, { Suspense, lazy } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import './App.css';

const Home = lazy(() => import('./Home'));
const TestPage = lazy(() => import('./components/TestPage/TestPage'));

export default function App(): JSX.Element {
  return (
    <Router>
      <Suspense fallback={<div>Loading...</div>}>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/TestPage" component={TestPage} />
        </Switch>
      </Suspense>
    </Router>
  );
}

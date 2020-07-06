import React from 'react';
import ForkMe from './ForkMe';
import './App.css';
import './ForkMe.css';
import Footer from './Footer';
import SearchForm from './SearchForm';

export default function Home(): JSX.Element {
  return (
    <>
      <ForkMe />
      <div className="container-fluid w-100" style={{ margin: 'auto' }}>
        <SearchForm />
        <Footer />
      </div>
    </>
  );
}

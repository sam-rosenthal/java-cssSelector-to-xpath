import React from 'react';
import ForkMe from './components/ForkMe/ForkMe';
import Footer from './components/Footer/Footer';
import SearchForm from './components/SearchForm/SearchForm';

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

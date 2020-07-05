import React from 'react';
import Modal from 'react-bootstrap/Modal';

export default function PopUpModal(props: { title: string; body: JSX.Element }): JSX.Element {
  const [isOpen, setIsOpen] = React.useState(false);

  const showModal = () => {
    setIsOpen(true);
  };

  const hideModal = () => {
    setIsOpen(false);
  };

  return (
    <>
      <button onClick={showModal}>{props.title}</button>
      <Modal show={isOpen} onHide={hideModal}>
        <Modal.Header>
          <Modal.Title>{props.title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>{props.body}</Modal.Body>
        <Modal.Footer>
          <button onClick={hideModal}>Cancel</button>
          <button>Save</button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

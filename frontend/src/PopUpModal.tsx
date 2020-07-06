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
      <a
        id={props.title}
        className="btn btn-link"
        onClick={showModal}
        style={{ color: '#264de4', paddingTop: '0px', borderTop: '0px' }}
      >
        {props.title}
      </a>
      <Modal scrollable={true} show={isOpen} onHide={hideModal}>
        <Modal.Header>
          <Modal.Title>{props.title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>{props.body}</Modal.Body>
        <Modal.Footer>
          <button className="btn" onClick={hideModal}>
            Cancel
          </button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

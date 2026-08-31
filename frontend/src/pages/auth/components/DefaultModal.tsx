import Modal from "react-modal";

type DefaultModalProps = {
  isOpen: boolean;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  children: React.ReactElement;
};

const DefaultModal = ({ isOpen, setIsOpen, children }: DefaultModalProps) => {
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => setIsOpen(false)}
      contentLabel="default_modal"
      style={{
        overlay: {
          backgroundColor: "rgba(0, 0, 0, 0.5)",
        },
        content: {
          top: "50%",
          left: "50%",
          right: "auto",
          bottom: "auto",
          marginRight: "-50%",
          transform: "translate(-50%, -50%)",
          maxWidth: "800px",
          maxHeight: "400px",
          overflow: "auto",
          color: "#000000",
          backgroundColor: "#f0aa50",
          boxShadow: "10px 10px 20px rgba(0, 0, 0, 0.5)",
        },
      }}
    >
      {children}
    </Modal>
  );
};

export default DefaultModal;

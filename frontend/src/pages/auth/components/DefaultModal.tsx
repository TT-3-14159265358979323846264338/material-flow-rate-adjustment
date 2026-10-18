import Modal from "react-modal";

type DefaultModalProps = {
  isOpen?: boolean;
  setIsOpen?: React.Dispatch<React.SetStateAction<boolean>> | (() => void);
  children: React.ReactElement;
};

const DefaultModal = ({ isOpen = false, setIsOpen, children }: DefaultModalProps) => {
  const onRequestClose = () => {
    if (!setIsOpen) {
      return;
    }
    if (setIsOpen.length === 1) {
      (setIsOpen as React.Dispatch<React.SetStateAction<boolean>>)(false);
    }else{
      (setIsOpen as () => void)();
    }
  };
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onRequestClose}
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
          maxHeight: "600px",
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
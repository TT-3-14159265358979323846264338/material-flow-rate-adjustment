type DefaultButtonProps = {
  children: string;
  onClick: () => void;
}

const DefaultButton = ({children, onClick}: DefaultButtonProps) => (
  <button
    type="button"
    onClick={onClick}
    className="default-button mt-3 w-30"
  >
    {children}
  </button>
);

export default DefaultButton;
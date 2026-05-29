type SubmitButtonProps = {
  children: string;
}

const SubmitButton = ({children}: SubmitButtonProps) => (
  <button
    type="submit"
    className="default-button mt-3"
  >
    {children}
  </button>
);

export default SubmitButton;
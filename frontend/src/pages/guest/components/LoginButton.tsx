type LoginButtonProps = {
  children: string;
}

const LoginButton = ({children}: LoginButtonProps) => (
  <button
    type="submit"
    className="default-button"
  >
    {children}
  </button>
);

export default LoginButton;
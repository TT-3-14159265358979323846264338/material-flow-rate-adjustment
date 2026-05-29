import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import LoginForm from './LoginForm';
import * as useLoginModule from './hooks/useLogin';

vi.mock('./hooks/useLogin', () => ({
  useLogin: vi.fn(),
}));

describe('LoginForm', () => {
  const mockHandleSubmit = vi.fn();
  const mockSetUser = vi.fn();
  const mockSetPassword = vi.fn();

  beforeEach(() => {
    vi.clearAllMocks();
    (useLoginModule.useLogin as any).mockReturnValue({
      user: '',
      setUser: mockSetUser,
      password: '',
      setPassword: mockSetPassword,
      handleSubmit: mockHandleSubmit,
    });
    render(<LoginForm />);
  });

  it('should call handleSubmit', () => {
    const loginButton = screen.getByRole('button', { name: 'ログイン' });
    fireEvent.submit(loginButton);
    expect(mockHandleSubmit).toHaveBeenCalledTimes(1);
  });

  it.each([
    { testId: 'username-input', mock: mockSetUser },
    { testId: 'password-input', mock: mockSetPassword },
  ])('should change $testId', ({ testId, mock }) => {
    const inputElement = screen.getByTestId(testId);
    fireEvent.change(inputElement, { target: { value: 'Change' } });
    expect(mock).toHaveBeenCalledWith('Change');
  });
});
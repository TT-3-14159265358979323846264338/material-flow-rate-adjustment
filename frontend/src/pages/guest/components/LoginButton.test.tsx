import { render, screen } from '@testing-library/react';
import { describe, it, expect, beforeEach } from 'vitest';
import LoginButton from './LoginButton';

describe('LoginButton', () => {
  beforeEach(() => {
    render(<LoginButton>ログイン</LoginButton>);
  });

  it('should input children', () => {
    const buttonElement = screen.getByText('ログイン');
    expect(buttonElement).toBeInTheDocument();
  });

  it('should have submit type', () => {
    const buttonElement = screen.getByRole('button');
    expect(buttonElement).toHaveAttribute('type', 'submit');
  });
});
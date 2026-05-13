import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import PasswordInput from './PasswordInput';

describe('PasswordInput', () => {
  const mockEvent = vi.fn();
  let inputElement: HTMLElement;

  beforeEach(() => {
    vi.clearAllMocks();
    render(<PasswordInput value="" onChange={mockEvent}/>);
    inputElement = screen.getByTestId('password-input');
  });

  it('should call onChange', () => {
    fireEvent.change(inputElement, { target: { value: 'Change' } });
    expect(mockEvent).toHaveBeenCalledTimes(1);
  });

  it('should have required type', () => {
    expect(inputElement).toBeRequired();
  });

  it('should have password type', () => {
    expect(inputElement).toHaveAttribute('type', 'password');
  });
});
import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import UserInput from './UserInput';

describe('UserInput', () => {
  const mockEvent = vi.fn();
  let inputElement: HTMLElement;

  beforeEach(() => {
    vi.clearAllMocks();
    render(<UserInput value="" onChange={mockEvent} />);
    inputElement = screen.getByTestId('username-input');
  });

  it('should call onChange', () => {
    fireEvent.change(inputElement, { target: { value: 'Change' } });
    expect(mockEvent).toHaveBeenCalledTimes(1);
  });

  it('should have required type', () => {
    expect(inputElement).toBeRequired();
  });
});
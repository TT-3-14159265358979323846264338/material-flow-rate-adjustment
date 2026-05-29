import { renderHook, act, RenderHookResult } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { SyntheticEvent,  } from 'react';
import { useLogin } from './useLogin';

describe('useLogin', () => {
  let hook: RenderHookResult<ReturnType<typeof useLogin>, any>;

  beforeEach(() => {
    hook = renderHook(() => useLogin());
  });

  it.each([
    { field: 'user', setter: 'setUser' },
    { field: 'password', setter: 'setPassword' },
  ])('should change $field', ({ field, setter }) => {
    act(() => {
      (hook.result.current as any)[setter]('Test');
    });
    expect((hook.result.current as any)[field]).toBe('Test');
  });

  it('should call preventDefault', () => {
    const mockEvent = {
      preventDefault: vi.fn(),
    } as unknown as SyntheticEvent;
    act(() => {
      hook.result.current.handleSubmit(mockEvent);
    });
    expect(mockEvent.preventDefault).toHaveBeenCalledTimes(1);
  });
});
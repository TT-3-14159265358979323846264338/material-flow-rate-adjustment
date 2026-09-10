export const defaultEvent = (
  e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement, Element>,
  correctValue: (value: string) => any,
) => {
  const { name, value } = e.currentTarget;
  return {
    currentTarget: {
      name: name,
      value: correctValue(value),
    },
  } as unknown as React.ChangeEvent<HTMLSelectElement>;
};

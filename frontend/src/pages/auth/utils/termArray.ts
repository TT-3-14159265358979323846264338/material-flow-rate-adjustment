export const yearArray = () => {
  const startTerm = 2025;
  const yeraTerm = new Date().getFullYear() - startTerm + 1;
  return Array.from({ length: yeraTerm }, (_, i) => String(i + startTerm));
};

export const monthArray = () => {
  return Array.from({ length: 12 }, (_, i) => String(i + 1));
};